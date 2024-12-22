import java.util.ArrayList;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String roomType;
    private boolean isBooked;
    private String guestName;

    public Room(int roomNumber, String roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isBooked = false;
        this.guestName = null;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public String getGuestName() {
        return guestName;
    }

    public void bookRoom(String guestName) {
        this.isBooked = true;
        this.guestName = guestName;
    }

    public void freeRoom() {
        this.isBooked = false;
        this.guestName = null;
    }
}

class Hotel {
    private ArrayList<Room> rooms;
    public Hotel() {
        rooms = new ArrayList<>();
    }

    public void addRoom(int roomNumber, String roomType) {
        rooms.add(new Room(roomNumber, roomType));
    }

    public void displayAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked()) {
                System.out.println("Room Number: " + room.getRoomNumber() + ", Type: " + room.getRoomType());
            }
        }
    }

    public boolean bookRoom(int roomNumber, String guestName) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (!room.isBooked()) {
                    room.bookRoom(guestName);
                    System.out.println("Room " + roomNumber + " has been successfully booked for " + guestName + ".");
                    return true;
                } else {
                    System.out.println("Room " + roomNumber + " is already booked.");
                    return false;
                }
            }
        }
        System.out.println("Room " + roomNumber + " not found.");
        return false;
    }

    public void displayBookingDetails() {
        System.out.println("\nBooking Details:");
        for (Room room : rooms) {
            if (room.isBooked()) {
                System.out.println("Room Number: " + room.getRoomNumber() + ", Type: " + room.getRoomType() + ", Guest: " + room.getGuestName());
            }
        }
    }
    public boolean freeRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (room.isBooked()) {
                    room.freeRoom();
                    System.out.println("Room " + roomNumber + " has been successfully freed.");
                    return true;
                } else {
                    System.out.println("Room " + roomNumber + " is not currently booked.");
                    return false;
                }
            }
        }
        System.out.println("Room " + roomNumber + " not found.");
        return false;
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();

        hotel.addRoom(101, "Single");
        hotel.addRoom(102, "Double");
        hotel.addRoom(103, "Suite");

        boolean exit = false;

        while (!exit) {
            System.out.println("\nHotel Booking System");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View Booking Details");
            System.out.println("4. Free a Room");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    hotel.displayAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter Room Number to Book: ");
                    int roomNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Guest Name: ");
                    String guestName = scanner.nextLine();
                    hotel.bookRoom(roomNumber, guestName);
                    break;
                case 3:
                    hotel.displayBookingDetails();
                    break;
                case 4:
                    System.out.print("Enter Room Number to Free: ");
                    int roomToFree = scanner.nextInt();
                    hotel.freeRoom(roomToFree);
                    break;
                case 5:
                    System.out.println("Exiting the system...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
