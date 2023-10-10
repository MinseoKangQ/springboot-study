package dev.MinseoKangQ.jpa;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ValidTestDto {

    @NotNull // 변수가 null 인지 검증
    private String notNullString;

    @NotEmpty // null 이 아니면서 문자열 변수는 선언됨 ("" 검증 실패)
    private String notEmptyString;

    @NotBlank // 공백이 아닌 문자열 ("         " 검증 실패)
    private String notBlankString;

    @NotEmpty // null 이 아니면서 배열 변수는 선언됨 ([] 검증 실패)
    private List<String> notEmptyStringList;

    public ValidTestDto() {
    }

    public ValidTestDto(String notNullString, String notEmptyString, String notBlankString, List<String> notEmptyStringList) {
        this.notNullString = notNullString;
        this.notEmptyString = notEmptyString;
        this.notBlankString = notBlankString;
        this.notEmptyStringList = notEmptyStringList;
    }

    public String getNotNullString() {
        return notNullString;
    }

    public void setNotNullString(String notNullString) {
        this.notNullString = notNullString;
    }

    public String getNotEmptyString() {
        return notEmptyString;
    }

    public void setNotEmptyString(String notEmptyString) {
        this.notEmptyString = notEmptyString;
    }

    public String getNotBlankString() {
        return notBlankString;
    }

    public void setNotBlankString(String notBlankString) {
        this.notBlankString = notBlankString;
    }

    public List<String> getNotEmptyStringList() {
        return notEmptyStringList;
    }

    public void setNotEmptyStringList(List<String> notEmptyStringList) {
        this.notEmptyStringList = notEmptyStringList;
    }

    @Override
    public String toString() {
        return "ValidTestDto{" +
                "notNullString='" + notNullString + '\'' +
                ", notEmptyString='" + notEmptyString + '\'' +
                ", notBlankString='" + notBlankString + '\'' +
                ", notEmptyStringList=" + notEmptyStringList +
                '}';
    }
}
