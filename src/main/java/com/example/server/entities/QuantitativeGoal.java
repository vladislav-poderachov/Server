package com.example.server.entities;

import com.example.server.enums.GoalType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "quantitative_goals")
public class QuantitativeGoal extends Goal {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measure_unit_id")
    private MeasureUnit measureUnit;
    
    private String customMeasureUnit;
    
    @Column(nullable = false)
    private double targetValue;
    
    private double currentValue;
    
    @Column(nullable = false)
    private String valueUnit;
    
    @Column(nullable = false)
    private double timeTarget;
    
    @Column(nullable = false)
    private String timeUnit;
    
    private double currentTimeValue;

    public QuantitativeGoal() {
        setType(GoalType.QUANTITATIVE);
    }
} 