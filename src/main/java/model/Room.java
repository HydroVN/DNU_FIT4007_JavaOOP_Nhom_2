package main.java.model;

public class Room {
    private int roomId;
    private String roomName;
    private int seatCount;

    public Room(int roomId, String roomName, int seatCount) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.seatCount = seatCount;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
}
