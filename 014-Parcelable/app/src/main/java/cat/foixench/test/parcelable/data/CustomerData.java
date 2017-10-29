package cat.foixench.test.parcelable.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by llorenc on 29/10/2017.
 */

public class CustomerData implements Parcelable {

    private long id;
    private String customerName;
    private String address;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator () {

        @Override
        public CustomerData createFromParcel(Parcel in) {
            return new CustomerData(in);
        }

        public CustomerData[] newArray(int size) {
            return new CustomerData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    // para crear un parcel a partir de una instancia de esta clase
    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeLong(this.getId());
        dest.writeString(this.getCustomerName());
        dest.writeString(this.getAddress());
    }

    // constuctors
    public CustomerData(long id, String name, String address) {
        this.setId(id);
        this.setCustomerName(name);
        this.setAddress(address);
    }

    // constructor para cuando se rcibe un objeto parcelable.
    public CustomerData(Parcel in) {
        this.setId(in.readLong());
        this.setCustomerName(in.readString());
        this.setAddress(in.readString());
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
