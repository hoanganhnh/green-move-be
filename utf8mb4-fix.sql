-- Set the database to use utf8mb4 character set
ALTER DATABASE boilerplate CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Update the RENTALS table to use utf8mb4
ALTER TABLE RENTALS CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Specifically update the pickup_location column
ALTER TABLE RENTALS MODIFY pickup_location TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Show the updated table structure
SHOW CREATE TABLE RENTALS;
