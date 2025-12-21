package com.kerem.bootstrap;

import com.kerem.entities.Car;
import com.kerem.entities.Customer;
import com.kerem.entities.ExtraService;
import com.kerem.entities.Location;
import com.kerem.entities.Reservation;
import com.kerem.repository.CarRepository;
import com.kerem.repository.CustomerRepository;
import com.kerem.repository.ExtraServiceRepository;
import com.kerem.repository.LocationRepository;
import com.kerem.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

        private final CarRepository carRepository;
        private final LocationRepository locationRepository;
        private final CustomerRepository customerRepository;
        private final ExtraServiceRepository extraServiceRepository;
        private final ReservationRepository reservationRepository;

        @Override
        @Transactional
        public void run(String... args) {
                if (locationRepository.count() > 0) {
                        System.out.println("Data already initialized. Skipping...");
                        return;
                }

                System.out.println("Initializing data...");

                // 1. Locations
                List<Location> locations = new ArrayList<>();
                locations.add(createLocation("Istanbul Airport")); // 1
                locations.add(createLocation("Sabiha Gökçen Airport")); // 2
                locations.add(createLocation("Istanbul Downtown Office")); // 3
                locations.add(createLocation("Ankara Esenboğa Airport")); // 4
                locations.add(createLocation("Izmir Adnan Menderes Airport")); // 5
                locations.add(createLocation("Antalya Airport")); // 6
                locations.add(createLocation("Bursa City Center")); // 7
                locations.add(createLocation("Adana Şakirpaşa Airport")); // 8
                locations.add(createLocation("Trabzon Airport")); // 9
                locations.add(createLocation("Kayseri Airport")); // 10

                locations = locationRepository.saveAll(locations);

                // 2. Extra Services
                List<ExtraService> extras = new ArrayList<>();
                // DRIVER
                extras.add(createExtraService("Additional Driver", "Register another driver for the vehicle.", 25.00,
                                ExtraService.ExtraCategory.DRIVER));
                extras.add(createExtraService("Young Driver Fee", "Fee for drivers under 25.", 40.00,
                                ExtraService.ExtraCategory.DRIVER));
                extras.add(createExtraService("Roadside Assistance Plus", "Enhanced roadside rescue package.", 15.00,
                                ExtraService.ExtraCategory.DRIVER));
                // CHILD
                extras.add(createExtraService("Infant Car Seat", "Seat suitable for infants up to 1 year.", 30.00,
                                ExtraService.ExtraCategory.CHILD));
                extras.add(createExtraService("Toddler Car Seat", "Seat for children aged 1–4.", 30.00,
                                ExtraService.ExtraCategory.CHILD));
                extras.add(createExtraService("Booster Seat", "Seat for children aged 4–10.", 20.00,
                                ExtraService.ExtraCategory.CHILD));
                // TECHNOLOGY
                extras.add(createExtraService("GPS Navigation", "Portable GPS device.", 35.00,
                                ExtraService.ExtraCategory.TECHNOLOGY));
                extras.add(createExtraService("WiFi Hotspot Router", "Portable wireless internet device.", 40.00,
                                ExtraService.ExtraCategory.TECHNOLOGY));
                extras.add(createExtraService("USB Charger Kit", "Phone charger and cable set.", 10.00,
                                ExtraService.ExtraCategory.TECHNOLOGY));
                extras.add(createExtraService("Dash Camera", "Dashboard-mounted security camera.", 25.00,
                                ExtraService.ExtraCategory.TECHNOLOGY));
                // WINTER
                extras.add(createExtraService("Snow Chains", "Metal chains for snow conditions.", 15.00,
                                ExtraService.ExtraCategory.WINTER));
                extras.add(createExtraService("Snow Tires", "Full winter tire package.", 50.00,
                                ExtraService.ExtraCategory.WINTER));
                extras.add(createExtraService("Ski Rack", "Rack for ski equipment.", 20.00,
                                ExtraService.ExtraCategory.WINTER));
                extras.add(
                                createExtraService("Snowboard Rack", "Rack for snowboards.", 20.00,
                                                ExtraService.ExtraCategory.WINTER));
                // CONVENIENCE
                extras.add(createExtraService("Toll Pass (HGS/OGS)", "Electronic toll transponder.", 10.00,
                                ExtraService.ExtraCategory.CONVENIENCE));
                extras.add(createExtraService("Roof Rack", "Standard roof rack for luggage.", 15.00,
                                ExtraService.ExtraCategory.CONVENIENCE));
                extras.add(createExtraService("Roof Box", "Extra storage box mounted on roof.", 30.00,
                                ExtraService.ExtraCategory.CONVENIENCE));
                extras.add(createExtraService("Prepaid Fuel", "Full tank prepaid option.", 70.00,
                                ExtraService.ExtraCategory.CONVENIENCE));
                // INSURANCE
                extras.add(createExtraService("Collision Damage Waiver", "Reduces responsibility for damage.", 80.00,
                                ExtraService.ExtraCategory.INSURANCE));
                extras.add(createExtraService("Theft Protection", "Insurance for vehicle theft.", 50.00,
                                ExtraService.ExtraCategory.INSURANCE));
                extras.add(createExtraService("Glass & Tire Protection", "Covers damage to windows and tires.", 35.00,
                                ExtraService.ExtraCategory.INSURANCE));
                // SPECIAL
                extras.add(createExtraService("Cross-Border Permit", "Legal approval to drive across countries.',",
                                40.00,
                                ExtraService.ExtraCategory.SPECIAL));
                extras.add(createExtraService("Car Delivery Service", "Car delivered to customer location.", 25.00,
                                ExtraService.ExtraCategory.SPECIAL));

                extras = extraServiceRepository.saveAll(extras);

                // 3. Cars
                List<Car> cars = new ArrayList<>();
                // 1. Toyota Corolla - Loc: Istanbul Airport (0)
                cars.add(createCar("34 ABC 123", 5, "Toyota", "Corolla", 45000L, Car.TransmissionType.AUTOMATIC, 45.00,
                                Car.Category.COMPACT, "Economy compact sedan.", locations.get(0),
                                Car.CarStatus.IN_SERVICE));

                // 2. Honda Civic - Loc: Istanbul Airport (0)
                cars.add(createCar("06 XYZ 789", 5, "Honda", "Civic", 55000L, Car.TransmissionType.MANUAL, 50.00,
                                Car.Category.MID_SIZE, "Mid-size family car.", locations.get(0),
                                Car.CarStatus.IN_SERVICE));

                // 3. VW Touran - Loc: Sabiha Gokcen (1)
                cars.add(createCar("35 KRM 456", 7, "Volkswagen", "Touran", 60000L, Car.TransmissionType.AUTOMATIC,
                                65.00,
                                Car.Category.VAN, "Spacious family van.", locations.get(1), Car.CarStatus.IN_SERVICE));

                // 4. BMW 320i - Loc: Downtown (2)
                cars.add(createCar("16 RNT 222", 5, "BMW", "320i", 30000L, Car.TransmissionType.AUTOMATIC, 95.00,
                                Car.Category.LUXURY, "Premium luxury sedan.", locations.get(2),
                                Car.CarStatus.IN_SERVICE));

                // 5. Tesla Model 3 - Loc: Sabiha Gokcen (1)
                cars.add(createCar("34 ELC 001", 5, "Tesla", "Model 3", 20000L, Car.TransmissionType.AUTOMATIC, 120.00,
                                Car.Category.ELECTRIC, "Fully electric performance sedan.", locations.get(1),
                                Car.CarStatus.IN_SERVICE));

                // 6. Hyundai Santa Fe - Loc: Istanbul Airport (0) - OUT_OF_SERVICE
                cars.add(createCar("07 SUV 555", 7, "Hyundai", "Santa Fe", 70000L, Car.TransmissionType.AUTOMATIC,
                                80.00,
                                Car.Category.SUV, "Large SUV with spacious interior.", locations.get(0),
                                Car.CarStatus.OUT_OF_SERVICE));

                // 7. Toyota Prius - Loc: Downtown (2)
                cars.add(createCar("01 HYB 888", 5, "Toyota", "Prius", 50000L, Car.TransmissionType.AUTOMATIC, 70.00,
                                Car.Category.HYBRID, "Fuel-efficient hybrid.", locations.get(2),
                                Car.CarStatus.IN_SERVICE));

                // 8. Ford F-150 - Loc: Istanbul Airport (0)
                cars.add(createCar("35 TRK 900", 2, "Ford", "F-150", 85000L, Car.TransmissionType.MANUAL, 110.00,
                                Car.Category.TRUCK, "Heavy-duty pickup truck.", locations.get(0),
                                Car.CarStatus.IN_SERVICE));

                // 9. Mercedes C200 - Loc: Sabiha Gokcen (1)
                cars.add(createCar("42 FUL 777", 5, "Mercedes", "C200", 40000L, Car.TransmissionType.AUTOMATIC, 100.00,
                                Car.Category.FULL_SIZE, "Full-size comfortable sedan.", locations.get(1),
                                Car.CarStatus.IN_SERVICE));

                // 10. Volvo V60 - Loc: Downtown (2)
                cars.add(createCar("33 SWN 640", 5, "Volvo", "V60", 43000L, Car.TransmissionType.AUTOMATIC, 85.00,
                                Car.Category.STATION_WAGON, "Station wagon with large trunk.", locations.get(2),
                                Car.CarStatus.IN_SERVICE));

                cars = carRepository.saveAll(cars);

                // 4. Customers
                List<Customer> customers = new ArrayList<>();
                customers.add(createCustomer("11223344556", "Kerem", "Yılmaz", "kerem.yilmaz@example.com",
                                "+905551234567",
                                "Kadikoy, Istanbul", "TR-34-1001"));
                customers.add(createCustomer("22334455667", "Ayşe", "Demir", "ayse.demir@testmail.com", "+905329876543",
                                "Cankaya, Ankara", "TR-06-2023"));
                customers.add(createCustomer("33445566778", "Mehmet", "Öztürk", "mehmet.ozturk@company.org",
                                "+905421112233",
                                "Konak, Izmir", "TR-35-8899"));
                customers.add(createCustomer("44556677889", "Zeynep", "Kaya", "zeynep.kaya@provider.net",
                                "+905056667788",
                                "Muratpasa, Antalya", "TR-07-5544"));
                customers.add(createCustomer("99887766554", "John", "Smith", "john.smith@global.com", "+905304445566",
                                "Besiktas, Istanbul", "UK-LN-998877"));

                customers = customerRepository.saveAll(customers);

                // 5. Reservations
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // Reservation 1
                Reservation r1 = new Reservation();
                r1.setCreationDate(LocalDateTime.parse("2025-10-01 09:00:00", formatter));
                r1.setPickUpDateAndTime(LocalDateTime.parse("2025-10-15 10:00:00", formatter));
                r1.setDropOffDateAndTime(LocalDateTime.parse("2025-10-20 10:00:00", formatter));
                r1.setStatus(Reservation.Status.COMPLETED);
                r1.setPickUpLocation(locations.get(0)); // Istanbul Airport
                r1.setDropOffLocation(locations.get(1)); // Sabiha
                r1.setCustomer(customers.get(0)); // Kerem
                r1.setCar(cars.get(0)); // Corolla
                r1.getExtras().add(extras.get(6)); // GPS (Index 6)
                r1.getExtras().add(extras.get(17)); // Prepaid Fuel (Index 17)
                reservationRepository.save(r1);

                // Reservation 2
                Reservation r2 = new Reservation();
                r2.setCreationDate(LocalDateTime.parse("2025-11-15 14:30:00", formatter));
                r2.setPickUpDateAndTime(LocalDateTime.parse("2025-12-01 09:00:00", formatter));
                r2.setDropOffDateAndTime(LocalDateTime.parse("2025-12-10 18:00:00", formatter));
                r2.setStatus(Reservation.Status.ACTIVE);
                r2.setPickUpLocation(locations.get(2)); // Sabiha Gökçen Airport
                r2.setDropOffLocation(locations.get(3)); // Istanbul Downtown Office
                r2.setCustomer(customers.get(1)); // Ayse
                r2.setCar(cars.get(3)); // BMW 320i
                r2.getExtras().add(extras.get(3)); // Infant Car Seat (Index 3 - ID 4)
                r2.getExtras().add(extras.get(18)); // Collision Damage (ID 19 is index 18)

                reservationRepository.save(r2);

                // R3 - Cancelled
                Reservation r3 = new Reservation();
                r3.setCreationDate(LocalDateTime.parse("2025-11-18 11:00:00", formatter));
                r3.setPickUpDateAndTime(LocalDateTime.parse("2025-12-05 12:00:00", formatter));
                r3.setDropOffDateAndTime(LocalDateTime.parse("2025-12-08 12:00:00", formatter));
                r3.setStatus(Reservation.Status.CANCELLED);
                r3.setPickUpLocation(locations.get(1)); // Sabiha
                r3.setDropOffLocation(locations.get(1));
                r3.setCustomer(customers.get(2)); // Mehmet
                r3.setCar(cars.get(4)); // Tesla
                reservationRepository.save(r3);

                // R4 - Future
                Reservation r4 = new Reservation();
                r4.setCreationDate(LocalDateTime.parse("2025-11-20 08:45:00", formatter));
                r4.setPickUpDateAndTime(LocalDateTime.parse("2025-12-28 10:00:00", formatter));
                r4.setDropOffDateAndTime(LocalDateTime.parse("2026-01-15 10:00:00", formatter));
                r4.setStatus(Reservation.Status.ACTIVE);
                r4.setPickUpLocation(locations.get(0)); // Istanbul
                r4.setDropOffLocation(locations.get(5)); // Antalya (ID 6 -> index 5)
                r4.setCustomer(customers.get(4)); // John
                r4.setCar(cars.get(8)); // Mercedes
                r4.getExtras().add(extras.get(21)); // Cross-Border (ID 22 -> index 21??)

                reservationRepository.save(r4);

                System.out.println("Data Initialized successfully.");
        }

        private Location createLocation(String name) {
                Location loc = new Location();
                loc.setLocationName(name);
                return loc;
        }

        private ExtraService createExtraService(String name, String desc, Double price,
                        ExtraService.ExtraCategory cat) {
                ExtraService ex = new ExtraService();
                ex.setName(name);
                ex.setDescription(desc);
                ex.setTotalPrice(price);
                ex.setCategory(cat);
                return ex;
        }

        private Car createCar(String plate, int seats, String brand, String model, Long mileage,
                        Car.TransmissionType trans,
                        Double price, Car.Category catType, String catDesc, Location loc, Car.CarStatus status) {
                Car car = new Car();
                car.setLicensePlateNumber(plate);
                car.setNumberOfSeats(seats);
                car.setBrand(brand);
                car.setModel(model);
                car.setMileage(mileage);
                car.setTransmissionType(trans);
                car.setDailyPrice(price);
                car.setCategoryType(catType);
                car.setCategoryDescription(catDesc);
                car.setLocation(loc);
                car.setStatus(status);
                return car;
        }

        private Customer createCustomer(String ssn, String first, String last, String email, String phone, String addr,
                        String license) {
                Customer c = new Customer();
                c.setSsn(ssn);
                c.setFirstName(first);
                c.setLastName(last);
                c.setEmail(email);
                c.setPhoneNumber(phone);
                c.setAddress(addr);
                c.setDrivingLicenseNumber(license);
                return c;
        }
}
