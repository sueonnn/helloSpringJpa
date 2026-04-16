package kr.ac.hansung.cse.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryForm {

    private Long id;

    @NotBlank(message = "카테고리명은 필수 입력 항목입니다.")
    @Size(max = 100, message = "카테고리명은 100자 이하여야 합니다.")
    private String name;

    public Category toEntity() {
        return new Category(this.name);
    }

    public static CategoryForm from(Category category) {
        CategoryForm form = new CategoryForm();
        form.id = category.getId();
        form.name = category.getName();
        return form;
    }
}
