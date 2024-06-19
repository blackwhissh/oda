package com.startup.oda.entity;

import com.startup.oda.entity.enums.TransactionEnum;
import com.startup.oda.entity.enums.TypeEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
@Entity
@Table(name = "property")
public class Property {
    @Id
    @SequenceGenerator(name = "property_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "property_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "property_id")
    private Long propertyId;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Owner owner;
    @ManyToOne
    @JoinColumn(name = "agent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Agent agent;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "has_pet")
    private Boolean hasPet;
    @Column(name = "city")
    private String city;
    @Column(name = "district")
    private String district;
    @Column(name = "rooms")
    private Integer rooms;
    @Column(name = "beds")
    private Integer beds;
    @Column(name = "area")
    private Integer area;
    @Column(name = "additional_info")
    private String additionalInfo;
    @Column(name = "creation_date")
    private final LocalDate creationDate = LocalDate.now();
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "type")
    private TypeEnum typeEnum;
    @Column(name = "transaction")
    private TransactionEnum transactionEnum;
    @Column(name = "is_active")
    private Boolean isActive;

    public Property() {
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getHasPet() {
        return hasPet;
    }

    public void setHasPet(Boolean hasPet) {
        this.hasPet = hasPet;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getBeds() {
        return beds;
    }

    public void setBeds(Integer beds) {
        this.beds = beds;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public TransactionEnum getTransactionEnum() {
        return transactionEnum;
    }

    public void setTransactionEnum(TransactionEnum transactionEnum) {
        this.transactionEnum = transactionEnum;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    @Override
    public String toString() {
        return "Property{" +
                "propertyId=" + propertyId +
                ", owner=" + owner +
                ", agent=" + agent +
                ", price=" + price +
                ", hasPet=" + hasPet +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", rooms=" + rooms +
                ", beds=" + beds +
                ", area=" + area +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", creationDate=" + creationDate +
                ", endDate=" + endDate +
                ", typeEnum=" + typeEnum +
                ", transactionEnum=" + transactionEnum +
                ", isActive=" + isActive +
                '}';
    }
}
