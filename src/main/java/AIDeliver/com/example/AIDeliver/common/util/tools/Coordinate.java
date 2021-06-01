package AIDeliver.com.example.AIDeliver.common.util.tools;


public class Coordinate {
    private String senderZipCode;
    private String receiverZipCode;

    public Coordinate(String senderZipCode, String receiverZipCode) {
        this.senderZipCode = senderZipCode;
        this.receiverZipCode = receiverZipCode;
    }


    public String getSenderZipCode() {
        return senderZipCode;
    }

    public void setSenderZipCode(String senderZipCode) {
        this.senderZipCode = senderZipCode;
    }

    public String getReceiverZipCode() {
        return receiverZipCode;
    }

    public void setReceiverZipCode(String receiverZipCode) {
        this.receiverZipCode = receiverZipCode;
    }
}


