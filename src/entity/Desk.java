package entity;

//餐桌实体类
public class Desk {
    private int id,seating;
    private String no,status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getSeating() {
        return seating;
    }

    public void setSeating(int seating) {
        this.seating = seating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
