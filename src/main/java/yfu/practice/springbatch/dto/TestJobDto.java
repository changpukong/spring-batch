package yfu.practice.springbatch.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestJobDto {

	@NotBlank(message = "str不得為空")
	private String str;
	
}