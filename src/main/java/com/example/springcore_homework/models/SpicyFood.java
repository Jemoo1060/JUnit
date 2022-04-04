package com.example.springcore_homework.models;

import com.example.springcore_homework.dto.FoodDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class SpicyFood {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SpicyRestaurant_ID")
    private SpicyRestaurant spicyRestaurant;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;



    public SpicyFood(FoodDto foodDto, SpicyRestaurant spicyRestaurant){
        this.name = foodDto.getName();
        this.price = foodDto.getPrice();
        this.spicyRestaurant = spicyRestaurant;
    }
}