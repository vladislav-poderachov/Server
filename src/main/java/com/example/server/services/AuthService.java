package services;

import dto.AuthRequest;
import dto.AuthResponse;
import dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
}
