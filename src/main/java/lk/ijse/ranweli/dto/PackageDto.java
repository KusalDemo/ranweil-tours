package lk.ijse.ranweli.dto;

public class PackageDto {
    private String packageId;
    private String packageName;
    private String description;
    private Double price;

    public PackageDto(){

    }

    public PackageDto(String packageId, String packageName, String description, Double price) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.description = description;
        this.price = price;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PackageDto{" +
                "packageId='" + packageId + '\'' +
                ", packageName='" + packageName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
