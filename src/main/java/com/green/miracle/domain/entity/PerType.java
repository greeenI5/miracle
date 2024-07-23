package com.green.miracle.domain.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PerType {
	MUSICAL("뮤지컬")
    ,CONCERT("콘서트")
    ,LECTURE("강연")
    ,MAGIC("마술")
    ,ROLEPLAY("연극")
    ;
	private final String typeName;

    public String typeName() {
        return typeName;
    }
    
 // 문자열을 PerType으로 변환하는 메서드
    public static PerType fromString(String typeName) {
        // PerType 열거형의 모든 값을 순회
        for (PerType perType : PerType.values()) {
            // 열거형 값의 typeName이 입력된 문자열과 같으면 해당 열거형 값을 반환
            if (perType.typeName.equalsIgnoreCase(typeName)) {
                return perType;
            }
        }
        // 일치하는 열거형 값이 없으면 예외를 발생
        throw new IllegalArgumentException("Invalid PerType: " + typeName);
    }
    
    // PerType을 문자열로 변환하는 메서드
    @Override
    public String toString() {
        return typeName;
    }
}
