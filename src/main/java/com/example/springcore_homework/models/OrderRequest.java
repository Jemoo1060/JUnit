package com.example.springcore_homework.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class OrderRequest {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String name;

    @Column(name = "FOODS")
    private Long orderId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;


    public OrderRequest(Food food, int quantity){
        this.name = food.getName();
        this.quantity = quantity;
        this.price = food.getPrice() * quantity;
    }


}
