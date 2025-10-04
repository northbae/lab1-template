package kz.bmstu.kritinina.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonResponse {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private Integer age;
    private String address;
    private String work;
}
