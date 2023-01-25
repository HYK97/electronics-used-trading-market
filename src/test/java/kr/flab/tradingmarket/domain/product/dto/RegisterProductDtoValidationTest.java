package kr.flab.tradingmarket.domain.product.dto;

import static kr.flab.tradingmarket.domain.product.controller.ProductControllerFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Set;

import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.LengthValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.flab.tradingmarket.common.validator.CategoryValidator;
import kr.flab.tradingmarket.common.validator.EnumValidator;
import kr.flab.tradingmarket.domain.category.service.CategoryService;
import kr.flab.tradingmarket.domain.product.dto.request.RegisterProductDto;

@ExtendWith(MockitoExtension.class)
class RegisterProductDtoValidationTest {

    Validator validator;

    @Mock
    CategoryService categoryService;

    @Mock
    ConstraintValidatorFactory cvf;

    void setUp() {
        when(cvf.getInstance(NotNullValidator.class)).thenReturn(
            new NotNullValidator());
        when(cvf.getInstance(CategoryValidator.class)).thenReturn(
            new CategoryValidator(categoryService));
        when(cvf.getInstance(EnumValidator.class)).thenReturn(
            new EnumValidator());
        when(cvf.getInstance(LengthValidator.class)).thenReturn(
            new LengthValidator());
        when(cvf.getInstance(NotBlankValidator.class)).thenReturn(
            new NotBlankValidator());
        when(cvf.getInstance(EnumValidator.class)).thenReturn(
            new EnumValidator());
        validator = Validation.buildDefaultValidatorFactory()
            .usingContext()
            .constraintValidatorFactory(cvf)
            .getValidator();
    }

    @Test
    @DisplayName("productStatus가 잘못된 값 일때 : validation 실패 테스트")
    void failProductStatusByInvalidStateValue() {
        //given
        setUp();
        given(categoryService.existCategory(any()))
            .willReturn(true);

        //when
        Set<ConstraintViolation<RegisterProductDto>> violations = validator.validate(
            FAIL_REGISTER_PRODUCT_DTO_VALIDATION_PRODUCT_STATUS);

        //then
        assertThat("잘못된 상태값 입니다.").isEqualTo(violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("productExchangeStatus가 잘못된 값 일때 : validation 실패 테스트")
    void failProductExchangeStatusByInvalidStateValue() {
        //given
        setUp();
        given(categoryService.existCategory(any()))
            .willReturn(true);

        //when
        Set<ConstraintViolation<RegisterProductDto>> violations = validator.validate(
            FAIL_REGISTER_PRODUCT_DTO_VALIDATION_PRODUCT_EXCHANGE_STATUS);

        //then
        assertThat("잘못된 상태값 입니다.").isEqualTo(violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 카테고리 번호일때  : validation 실패 테스트")
    void failCategoryByItDoesNotExist() {
        //given
        setUp();
        given(categoryService.existCategory(any()))
            .willReturn(false);

        //when
        Set<ConstraintViolation<RegisterProductDto>> violations = validator.validate(
            FAIL_REGISTER_PRODUCT_DTO_CATEGORY_DOES_NOT_EXIST);

        //then
        assertThat("존재하지 않는 카테고리 입니다.").isEqualTo(violations.iterator().next().getMessage());
    }

}