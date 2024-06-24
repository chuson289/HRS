-- Create and populate Hotel table
INSERT INTO hotel (name, address, destination) VALUES ('Hotel Sunshine', '123 Beach Ave', 'Beach City');
INSERT INTO hotel (name, address, destination) VALUES ('Mountain Retreat', '456 Hilltop Rd', 'Mountainville');
INSERT INTO hotel (name, address, destination) VALUES ('City Central', '789 Main St', 'Metro City');

-- Create and populate Reservation table
INSERT INTO reservation (guest_name, check_in_date, check_out_date, room_number, hotel_id) VALUES ('John Doe', '2024-07-01', '2024-07-07', 101, 1);
INSERT INTO reservation (guest_name, check_in_date, check_out_date, room_number, hotel_id) VALUES ('Jane Smith', '2024-07-05', '2024-07-10', 202, 2);
INSERT INTO reservation (guest_name, check_in_date, check_out_date, room_number, hotel_id) VALUES ('Alice Johnson', '2024-07-03', '2024-07-08', 303, 3);
INSERT INTO reservation (guest_name, check_in_date, check_out_date, room_number, hotel_id) VALUES ('Bob Brown', '2024-07-02', '2024-07-06', 104, 1);