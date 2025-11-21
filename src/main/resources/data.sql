INSERT INTO EXTRA_SERVICE (id, name, description, total_price, category) VALUES
-- DRIVER
(1,'Additional Driver', 'Register another driver for the vehicle.', 25.00, 'DRIVER'),
(2,'Young Driver Fee', 'Fee for drivers under 25.', 40.00, 'DRIVER'),
(3,'Roadside Assistance Plus', 'Enhanced roadside rescue package.', 15.00, 'DRIVER'),

-- CHILD
(4,'Infant Car Seat', 'Seat suitable for infants up to 1 year.', 30.00, 'CHILD'),
(5,'Toddler Car Seat', 'Seat for children aged 1–4.', 30.00, 'CHILD'),
(6,'Booster Seat', 'Seat for children aged 4–10.', 20.00, 'CHILD'),

-- TECHNOLOGY
(7,'GPS Navigation', 'Portable GPS device.', 35.00, 'TECHNOLOGY'),
(8,'WiFi Hotspot Router', 'Portable wireless internet device.', 40.00, 'TECHNOLOGY'),
(9,'USB Charger Kit', 'Phone charger and cable set.', 10.00, 'TECHNOLOGY'),
(10,'Dash Camera', 'Dashboard-mounted security camera.', 25.00, 'TECHNOLOGY'),

-- WINTER
(11,'Snow Chains', 'Metal chains for snow conditions.', 15.00, 'WINTER'),
(12,'Snow Tires', 'Full winter tire package.', 50.00, 'WINTER'),
(13,'Ski Rack', 'Rack for ski equipment.', 20.00, 'WINTER'),
(14,'Snowboard Rack', 'Rack for snowboards.', 20.00, 'WINTER'),

-- CONVENIENCE
(15,'Toll Pass (HGS/OGS)', 'Electronic toll transponder.', 10.00, 'CONVENIENCE'),
(16,'Roof Rack', 'Standard roof rack for luggage.', 15.00, 'CONVENIENCE'),
(17,'Roof Box', 'Extra storage box mounted on roof.', 30.00, 'CONVENIENCE'),
(18,'Prepaid Fuel', 'Full tank prepaid option.', 70.00, 'CONVENIENCE'),

-- INSURANCE
(19,'Collision Damage Waiver', 'Reduces responsibility for damage.', 80.00, 'INSURANCE'),
(20,'Theft Protection', 'Insurance for vehicle theft.', 50.00, 'INSURANCE'),
(21,'Glass & Tire Protection', 'Covers damage to windows and tires.', 35.00, 'INSURANCE'),

-- SPECIAL
(22,'Cross-Border Permit', 'Legal approval to drive across countries.', 40.00, 'SPECIAL'),
(23,'Car Delivery Service', 'Car delivered to customer location.', 25.00, 'SPECIAL')
ON CONFLICT DO NOTHING;

-- Postgres will now auto-generate 1, 2, 3, etc. for the 'code' column
INSERT INTO LOCATION (code, location_name) VALUES
                                         (1,'Istanbul Airport'),
                                         (2,'Sabiha Gökçen Airport'),
                                         (3,'Istanbul Downtown Office'),
                                         (4,'Ankara Esenboğa Airport'),
                                         (5,'Izmir Adnan Menderes Airport'),
                                         (6,'Antalya Airport'),
                                         (7,'Bursa City Center'),
                                         (8,'Adana Şakirpaşa Airport'),
                                         (9,'Trabzon Airport'),
                                         (10,'Kayseri Airport')
ON CONFLICT DO NOTHING;


INSERT INTO CAR (
    barcode,
    license_plate_number,
    number_of_seats,
    brand,
    model,
    mileage,
    transmission_type,
    daily_price,
    category_type,
    category_description,
    location_code,
    status
)
VALUES
    -- Toyota Corolla
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01', '34 ABC 123', 5, 'Toyota', 'Corolla', 45000, 'AUTOMATIC', 45.00,
     'COMPACT', 'Economy compact sedan.', 1, 'IN_SERVICE'),

    -- Honda Civic
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a02', '06 XYZ 789', 5, 'Honda', 'Civic', 55000, 'MANUAL', 50.00,
     'MID_SIZE', 'Mid-size family car.', 1, 'IN_SERVICE'),

    -- Volkswagen Touran (Kept as IN_SERVICE per original data, or change to IN_SERVICE if needed)
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a03', '35 KRM 456', 7, 'Volkswagen', 'Touran', 60000, 'AUTOMATIC', 65.00,
     'VAN', 'Spacious family van.', 2, 'IN_SERVICE'),

    -- BMW 320i
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a04', '16 RNT 222', 5, 'BMW', '320i', 30000, 'AUTOMATIC', 95.00,
     'LUXURY', 'Premium luxury sedan.', 3, 'IN_SERVICE'),

    -- Tesla Model 3
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05', '34 ELC 001', 5, 'Tesla', 'Model 3', 20000, 'AUTOMATIC', 120.00,
     'ELECTRIC', 'Fully electric performance sedan.', 2, 'IN_SERVICE'),

    -- Hyundai Santa Fe (Kept OUT_OF_SERVICE)
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a06', '07 SUV 555', 7, 'Hyundai', 'Santa Fe', 70000, 'AUTOMATIC', 80.00,
     'SUV', 'Large SUV with spacious interior.', 1, 'OUT_OF_SERVICE'),

    -- Toyota Prius
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a07', '01 HYB 888', 5, 'Toyota', 'Prius', 50000, 'AUTOMATIC', 70.00,
     'HYBRID', 'Fuel-efficient hybrid.', 3, 'IN_SERVICE'),

    -- Ford F-150
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a08', '35 TRK 900', 2, 'Ford', 'F-150', 85000, 'MANUAL', 110.00,
     'TRUCK', 'Heavy-duty pickup truck.', 1, 'IN_SERVICE'),

    -- Mercedes C200
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a09', '42 FUL 777', 5, 'Mercedes', 'C200', 40000, 'AUTOMATIC', 100.00,
     'FULL_SIZE', 'Full-size comfortable sedan.', 2, 'IN_SERVICE'),

    -- Volvo V60
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a10', '33 SWN 640', 5, 'Volvo', 'V60', 43000, 'AUTOMATIC', 85.00,
     'STATION_WAGON', 'Station wagon with large trunk.', 3, 'IN_SERVICE')
ON CONFLICT DO NOTHING;

INSERT INTO CUSTOMER (
    ssn,
    first_name,
    last_name,
    email,
    phone_number,
    address,
    driving_license_number
)
VALUES
-- Customer 1
('11223344556', 'Kerem', 'Yılmaz', 'kerem.yilmaz@example.com', '+905551234567', 'Kadikoy, Istanbul', 'TR-34-1001'),

-- Customer 2
('22334455667', 'Ayşe', 'Demir', 'ayse.demir@testmail.com', '+905329876543', 'Cankaya, Ankara', 'TR-06-2023'),

-- Customer 3
('33445566778', 'Mehmet', 'Öztürk', 'mehmet.ozturk@company.org', '+905421112233', 'Konak, Izmir', 'TR-35-8899'),

-- Customer 4
('44556677889', 'Zeynep', 'Kaya', 'zeynep.kaya@provider.net', '+905056667788', 'Muratpasa, Antalya', 'TR-07-5544'),

-- Customer 5 (Foreigner ID Example)
('99887766554', 'John', 'Smith', 'john.smith@global.com', '+905304445566', 'Besiktas, Istanbul', 'UK-LN-998877')

ON CONFLICT (ssn) DO NOTHING;

INSERT INTO RESERVATION (
    reservation_number,
    creation_date,
    pick_up_date_and_time,
    drop_off_date_and_time,
    status,
    pick_up_location_code,
    drop_off_location_code,
    customer_ssn,
    car_id
) VALUES
-- 1. PAST RESERVATION (COMPLETED)
-- Car: Toyota Corolla (ID 1)
-- Scenario: Customer returned the car last month. This car should be IN_SERVICE now.
(
 1,
    '2025-10-01 09:00:00',
    '2025-10-15 10:00:00',
    '2025-10-20 10:00:00',
    'COMPLETED',
    1, -- Istanbul Airport
    2, -- Sabiha Gökçen
    '11223344556', -- Kerem
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a01'
),

-- 2. ACTIVE RESERVATION (CONFLICT TEST)
-- Car: BMW 320i (ID 4)
-- Scenario: Currently rented or reserved for Dec 1-10.
-- Searching for dates between Dec 1-10 should NOT return this car.
(
 2,
    '2025-11-15 14:30:00',
    '2025-12-01 09:00:00',
    '2025-12-10 18:00:00',
    'ACTIVE',
    3, -- Downtown
    4, -- Downtown
    '22334455667', -- Ayşe
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a04'
),

-- 3. CANCELLED RESERVATION (FALSE ALARM TEST)
-- Car: Tesla Model 3 (ID 5)
-- Scenario: Customer booked for Dec 5-8 but cancelled.
-- Searching for these dates SHOULD return this car because the status is CANCELLED.
(
 3,
    '2025-11-18 11:00:00',
    '2025-12-05 12:00:00',
    '2025-12-08 12:00:00',
    'CANCELLED',
    5, -- Sabiha Gökçen
    6, -- Sabiha Gökçen
    '33445566778', -- Mehmet
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a05'
),

-- 4. FUTURE LONG TERM (ACTIVE)
-- Car: Mercedes C200 (ID 9)
-- Scenario: New Years rental.
(
 4,
    '2025-11-20 08:45:00',
    '2025-12-28 10:00:00',
    '2026-01-15 10:00:00',
    'ACTIVE',
    1, -- IST Airport
    1, -- Antalya Airport
    '99887766554', -- John (Foreigner)
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a09'
) ON CONFLICT DO NOTHING;

-- Link Extras to Reservations
INSERT INTO REF_RESERVATION_EXTRAS (reservation_number, extra_service_id) VALUES
-- Reservation 1 (Kerem, Corolla): Added GPS (7) and Prepaid Fuel (16)
(1, 7),
(1, 16),

-- Reservation 2 (Ayse, BMW): Added Child Seat (4) and Full Insurance (17 - Collision Damage)
(2, 4),
(2, 17),

-- Reservation 4 (John, Mercedes): Added Cross-Border Permit (20) and Snow Tires (12)
(4, 20),
(4, 12)
ON CONFLICT DO NOTHING;
