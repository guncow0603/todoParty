package com.thesun4sky.todoparty.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
	@Pattern(regexp = "^[a-z0-9]{4,10}$")
	private String username;

	@Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
	private String password;

	public void validate() {
		if (!username.matches("^[a-z0-9]{4,10}$")) {
			throw new IllegalArgumentException("유효하지 않은 사용자 이름 패턴입니다");
		}

		if (!password.matches("^[a-zA-Z0-9]{8,15}$")) {
			throw new IllegalArgumentException("유효하지 않은 비밀번호 패턴입니다");
		}
	}


}
