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
        for (PerType perType : PerType.values()) {
            if (perType.typeName.equalsIgnoreCase(typeName)) {
                return perType;
            }
        }
        throw new IllegalArgumentException("Invalid PerType: " + typeName);
    }
    
}
