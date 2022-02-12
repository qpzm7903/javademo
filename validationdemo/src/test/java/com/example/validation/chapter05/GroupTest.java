package com.example.validation.chapter05;

import com.example.validation.chapter05.groupinheritance.RaceCarChecks;
import com.example.validation.chapter05.groupinheritance.SuperCar;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        Locale.setDefault(new Locale("en", "CN"));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void test_() {
        Car car = new Car(null, "DD-AB-123", 2);
        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);
        assertEquals(1, constraintViolations.size());

        constraintViolations = validator.validate(car, CarChecks.class);

        car.setPassedVehicleInspection(false);
        constraintViolations.forEach(validation -> System.out.println(validation.getMessage()));
        assertEquals(1, constraintViolations.size());
    }

    @Test
    public void driveAway() {
        //tag::driveAway[]
        // create a car and check that everything is ok with it.
        Car car = new Car("Morris", "DD-AB-123", 2);
        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);
        assertEquals(0, constraintViolations.size());

        // but has it passed the vehicle inspection?
        constraintViolations = validator.validate(car, CarChecks.class);
        assertEquals(1, constraintViolations.size());
        assertEquals(
                "The car has to pass the vehicle inspection first",
                constraintViolations.iterator().next().getMessage()
        );

        // let's go to the vehicle inspection
        car.setPassedVehicleInspection(true);
        assertEquals(0, validator.validate(car, CarChecks.class).size());

        // now let's add a driver. He is 18, but has not passed the driving test yet
        Driver john = new Driver("John Doe");
        john.setAge(18);
        car.setDriver(john);
        constraintViolations = validator.validate(car, DriverChecks.class);
        assertEquals(1, constraintViolations.size());
        assertEquals(
                "You first have to pass the driving test",
                constraintViolations.iterator().next().getMessage()
        );

        // ok, John passes the test
        john.passedDrivingTest(true);
        assertEquals(0, validator.validate(car, DriverChecks.class).size());

        // just checking that everything is in order now
        assertEquals(
                0, validator.validate(
                        car,
                        Default.class,
                        CarChecks.class,
                        DriverChecks.class
                ).size()
        );
        //end::driveAway[]
    }

    @Test
    public void testGroupInheritance() {
        //tag::testGroupInheritance[]
        // create a supercar and check that it's valid as a generic Car
        SuperCar superCar = new SuperCar("Morris", "DD-AB-123", 1);
        assertEquals("must be greater than or equal to 2", validator.validate(superCar).iterator().next().getMessage());

        // check that this supercar is valid as generic car and also as race car
        Set<ConstraintViolation<SuperCar>> constraintViolations = validator.validate(superCar, RaceCarChecks.class);

        assertThat(constraintViolations).extracting("message").containsOnly(
                "Race car must have a safety belt",
                "must be greater than or equal to 2"
        );
        //end::testGroupInheritance[]
    }

    @Test
    public void testOrderedChecks() {
        //tag::testOrderedChecks[]
        Car car = new Car("Morris", "DD-AB-123", 2);
        car.setPassedVehicleInspection(true);

        Driver john = new Driver("John Doe");
        john.setAge(18);
        john.passedDrivingTest(true);
        car.setDriver(john);

        assertEquals(0, validator.validate(car, OrderedChecks.class).size());
        //end::testOrderedChecks[]
    }

    /**
     * Validating the default group leads to validation on the default group sequence of {@code RentalCar}.
     */
    @Test
    public void carIsRented() {
        //tag::carIsRented[]
        RentalCar rentalCar = new RentalCar("Morris", "DD-AB-123", 2);
        rentalCar.setPassedVehicleInspection(true);
        rentalCar.setRented(true);

        Set<ConstraintViolation<RentalCar>> constraintViolations = validator.validate(rentalCar);

        assertEquals(1, constraintViolations.size());
        assertEquals("The car is currently rented out", constraintViolations.iterator().next().getMessage());

        rentalCar.setRented(false);
        constraintViolations = validator.validate(rentalCar);

        assertEquals(0, constraintViolations.size());
        //end::carIsRented[]
    }
}
