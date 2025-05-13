package services.impl;

import config.JwtTokenUtil;
import dto.AuthRequest;
import dto.AuthResponse;
import dto.RegisterRequest;
import entities.User;
import enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repositories.UserRepository;
import services.AuthService;
import services.CustomUserDetailsService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        // Проверяем, существует ли пользователь с таким логином
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }

        // Создаем нового пользователя
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        // Сохраняем пользователя
        userRepository.save(user);

        // Получаем UserDetails и генерируем токен
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
        String token = jwtTokenUtil.generateToken(userDetails);

        // Создаем ответ
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setMessage("Регистрация прошла успешно");
        response.setRole(user.getRole());

        return response;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        // Аутентифицируем пользователя
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
            )
        );

        // Получаем UserDetails и генерируем токен
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
        String token = jwtTokenUtil.generateToken(userDetails);

        // Получаем пользователя для роли
        User user = userRepository.findByLogin(request.getLogin())
            .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Создаем ответ
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setMessage("Вход выполнен успешно");
        response.setRole(user.getRole());

        return response;
    }
}
