package objects;

import java.util.Objects;

public class BookingId {
    private int bookingid;
    private Booking booking;

    public BookingId(int bookingid, Booking booking) {
        this.bookingid = bookingid;
        this.booking = booking;
    }

    public BookingId() {
    }

    public int getBookingid() {
        return bookingid;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingId bookingId = (BookingId) o;
        return bookingid == bookingId.bookingid && Objects.equals(booking, bookingId.booking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingid, booking);
    }

    @Override
    public String toString() {
        return "objects.BookingId{" +
                "bookingid=" + bookingid +
                ", booking=" + booking +
                '}';
    }
}
