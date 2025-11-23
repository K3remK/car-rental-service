package com.kerem.repository.specification;

import com.kerem.entities.Car;
import com.kerem.entities.Reservation;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class CarSpecification {

    public static Specification<Car> hasCategory(String category) {
        return ((root, query, criteriaBuilder) -> {
            if (category == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("categoryType"), category);
        });
    }

    public static Specification<Car> hasTransmissionType(String transmissionType) {
        return ((root, query, criteriaBuilder) -> {
            if (transmissionType == null) {
                return null;
            }
            // TODO: might not work comparing an enum with string
            return criteriaBuilder.equal(root.get("transmissionType"), transmissionType);
        });
    }

    public static Specification<Car> isAvailable(LocalDateTime reqPickUp, LocalDateTime reqDropOff) {
        return ((root, query, criteriaBuilder) -> {
            if (reqPickUp == null || reqDropOff == null) {
                return criteriaBuilder.conjunction();
            }

            // subquery to find IDs of car that are busy
            Subquery<UUID> subquery = query.subquery(UUID.class);
            Root<Reservation> reservation = subquery.from(Reservation.class);

            // select the Car ID from the reservation table
            subquery.select(reservation.get("car").get("barcode"));

            // This is the overlap condition
            // A reservation overlaps if:
            // (ResStart < ReqDrop) AND (ResEnd > ReqStart)
            Predicate overlap = criteriaBuilder.and(
                    criteriaBuilder.lessThan(reservation.get("pickUpDateAndTime"), reqDropOff),
                    criteriaBuilder.greaterThan(reservation.get("dropOffDateAndTime"), reqPickUp)
            );

            // Check if the overlapping reservations is cancelled.
            // if so no need to consider it.
            Predicate activeStatus = criteriaBuilder.equal(reservation.get("status"), "ACTIVE");
            subquery.where(criteriaBuilder.and(overlap, activeStatus));

            // Main query: returns cars whose ID in NOT in the list of busy cars
            return criteriaBuilder.not(root.get("barcode").in(subquery));
        });
    }

    public static Specification<Car> priceInBetween(Double minPrice, Double maxPrice) {
        return (((root, query, criteriaBuilder) -> {
            if (maxPrice == null && minPrice == null) {
                return null;
            }
            if (minPrice == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("dailyPrice"), maxPrice);
            }
            if (maxPrice == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("dailyPrice"), minPrice);
            }
            return criteriaBuilder.between(root.get("dailyPrice"), minPrice, maxPrice);
        }));
    }

    public static Specification<Car> numberOfSeatsLessThan(Integer numberOfSeats) {
        return (((root, query, criteriaBuilder) -> {
            if (numberOfSeats == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("numberOfSeats"), numberOfSeats);
        }));
    }

    public static Specification<Car> numberOfSeatsGreaterThan(Integer numberOfSeats) {
        return (((root, query, criteriaBuilder) -> {
            if (numberOfSeats == null) {
                return null;
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfSeats"), numberOfSeats);
        }));
    }

    public static Specification<Car> hasBeenUsedForAnyReservation() {
        return ((root, query, criteriaBuilder) -> {

            Subquery<UUID> subquery = query.subquery(UUID.class);
            Root<Reservation> reservation = subquery.from(Reservation.class);

            subquery.select(reservation.get("car").get("barcode"));
            subquery.where(criteriaBuilder.equal(reservation.get("car").get("barcode"), root.get("barcode")));


            return criteriaBuilder.exists(subquery);
        });
    }

    public static Specification<Car> hasPickUpLocation(Long locationCode) {
        return ((root, query, criteriaBuilder) -> {
            if (locationCode == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("location").get("code"), locationCode);
        });
    }

    public static Specification<Car> hasStatus(String status) {
        return ((root, query, criteriaBuilder) -> {
            if (status == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("status"), status);
        });
    }

    public static Specification<Car> hasBrand(String brand) {
        return (((root, query, criteriaBuilder) -> {
            if (brand == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("brand"), brand);
        }));
    }

    public static Specification<Car> hasModel(String model) {
        return (((root, query, criteriaBuilder) -> {
            if (model == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("model"), model);
        }));
    }

    public static Specification<Car> hasMileageLessThan(Long maxMileage) {
        return (((root, query, criteriaBuilder) -> {
            if (maxMileage == null) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("mileage"), maxMileage);
        }));
    }

    public static Specification<Car> hasLicensePlate(String licensePlateNumber) {
        return (((root, query, criteriaBuilder) -> {
            if (licensePlateNumber == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("licensePlateNumber"), licensePlateNumber);
        }));
    }

    public static Specification<Car> hasId(UUID barcode) {
        return ((root, query, criteriaBuilder) -> {
            if (barcode == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("barcode"), barcode);
        });
    }
}
