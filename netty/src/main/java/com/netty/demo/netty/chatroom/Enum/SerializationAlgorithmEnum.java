package com.netty.demo.netty.chatroom.Enum;

public enum SerializationAlgorithmEnum {

    JDK(0);

    private Integer algorithmEnumType;

    SerializationAlgorithmEnum(Integer algorithmEnumType) {
        this.algorithmEnumType = algorithmEnumType;
    }

    public Integer getAlgorithmEnumType() {
        return algorithmEnumType;
    }

    public static SerializationAlgorithmEnum getByType(Integer algorithmEnumType){
        for(SerializationAlgorithmEnum algorithmEnum: SerializationAlgorithmEnum.values()){
            if (algorithmEnum.algorithmEnumType.equals(algorithmEnumType))
                return algorithmEnum;
        }
        return null;
    }
}
