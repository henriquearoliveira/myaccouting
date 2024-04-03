package br.com.contability.comum;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Getter
@Setter
@Component
public class RedirectAttributesAbstract {

    private RedirectAttributesAbstract() {
    }

    private RedirectAttributes redirectAttributes;
}
