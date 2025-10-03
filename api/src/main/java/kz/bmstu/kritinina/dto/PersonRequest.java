package kz.bmstu.kritinina.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRequest {
    @NotBlank
    @Length(max = 250)
    private String name;
    @Min(0)
    private Integer age;
    @Length(max = 250)
    private String address;
    @Length(max = 250)
    private String work;
}
