package association.database.newDatabase.Exception;

import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class DataValidationException extends RuntimeException {
    private Set<? extends ConstraintViolation<?>> violations;

    public DataValidationException(String message, Set<? extends ConstraintViolation<?>> violations) {
        super(message);
        this.violations = violations;
    }

    public Set<? extends ConstraintViolation<?>> getViolations() {
        return violations;
    }
}