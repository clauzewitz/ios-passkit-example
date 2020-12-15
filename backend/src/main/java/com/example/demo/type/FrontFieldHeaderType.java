package com.example.demo.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FrontFieldHeaderType {
    NAME("이름"),
    MEMBERSHIP_NUM("멤버십 번호");

    private String name;
}
