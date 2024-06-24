### Prerequisites
- Ensure you have Postman installed. If not, you can download it from [here](https://www.postman.com/downloads/).

### Base URL
- The base URL for all endpoints is `http://localhost:8080/api/reservations` (assuming your application is running locally on port 8080).

### Endpoints

#### 1. Get All Reservations
- **Method:** GET
- **URL:** `http://localhost:8080/api/reservations`
- **Description:** Retrieves a list of all reservations.
- **Headers:** None
- **Body:** None
- **Expected Output:** A JSON array containing all reservations.

#### 2. Get Reservation by ID
- **Method:** GET
- **URL:** `http://localhost:8080/api/reservations/{id}`
- **Description:** Retrieves a specific reservation by its ID.
- **Headers:** None
- **URL Parameters:**
  - `id`: ID of the reservation to retrieve.
- **Body:** None
- **Expected Output:** A JSON object representing the reservation if found, or a 404 error if not found.

#### 3. Create Reservation
- **Method:** POST
- **URL:** `http://localhost:8080/api/reservations`
- **Description:** Creates a new reservation.
- **Headers:**
  - `Content-Type: application/json`
- **Body:** JSON object with the following fields:
  {
    "id": null, // Optional: ID is generated automatically by the server
    "guestName": "John Doe",
    "checkInDate": "2024-06-25",
    "checkOutDate": "2024-06-27",
    "roomNumber": "101"
    // Add other fields as required by your application
  }
- **Expected Output:** A JSON object representing the created reservation with HTTP status 201 (Created).

#### 4. Update Reservation
- **Method:** PUT
- **URL:** `http://localhost:8080/api/reservations/{id}`
- **Description:** Updates an existing reservation.
- **Headers:**
  - `Content-Type: application/json`
- **URL Parameters:**
  - `id`: ID of the reservation to update.
- **Body:** JSON object with the updated fields. Example:
  {
    "guestName": "Updated Guest",
    "checkInDate": "2024-06-25",
    "checkOutDate": "2024-06-28",
    "roomNumber": "101"
    // Update other fields as necessary
  }
- **Expected Output:** A JSON object representing the updated reservation with HTTP status 200 (OK), or a 404 error if the reservation with the given ID is not found.

#### 5. Delete Reservation
- **Method:** DELETE
- **URL:** `http://localhost:8080/api/reservations/{id}`
- **Description:** Deletes a reservation by its ID.
- **Headers:** None
- **URL Parameters:**
  - `id`: ID of the reservation to delete.
- **Body:** None
- **Expected Output:** HTTP status 204 (No Content) upon successful deletion, or a 404 error if the reservation with the given ID is not found.

#### 6. Get All Hotels
- **Method:** GET
- **URL:** `http://localhost:8080/api/hotels`
- **Description:** Retrieves a list of all hotels.
- **Headers:** None
- **Body:** None
- **Expected Output:** A JSON array containing all hotels.

#### 7. Search Hotels by Destination
- **Method:** GET
- **URL:** `http://localhost:8080/api/hotels/search?destination={destination}`
- **Description:** Searches hotels by destination.
- **Headers:** None
- **URL Parameters:**
  - `destination`: Destination to search for.
- **Body:** None
- **Expected Output:** A JSON array containing hotels matching the specified destination.

#### 8. Create Hotel
- **Method:** POST
- **URL:** `http://localhost:8080/api/hotels`
- **Description:** Creates a new hotel.
- **Headers:**
  - `Content-Type: application/json`
- **Body:** JSON object with the following fields:
  {
    "id": null, // Optional: ID is generated automatically by the server
    "name": "Sample Hotel",
    "address": "123 Main St, City",
    "destination": "Paris",
  }
- **Expected Output:** A JSON object representing the created hotel with HTTP status 201 (Created).
