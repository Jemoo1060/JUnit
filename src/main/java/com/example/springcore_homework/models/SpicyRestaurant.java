package com.example.springcore_homework.models;

import com.example.springcore_homework.dto.SpicyRestaurantDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class SpicyRestaurant {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int minOrderPrice;

    @Column(nullable = false)
    private int deliveryFee;

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    public SpicyRestaurant(SpicyRestaurantDto spicyRestaurantDto){
        this.name = spicyRestaurantDto.getName();
        this.minOrderPrice = spicyRestaurantDto.getMinOrderPrice();
        this.deliveryFee = spicyRestaurantDto.getDeliveryFee();
        this.x = spicyRestaurantDto.getX();
        this.y = spicyRestaurantDto.getY();
    }
}
