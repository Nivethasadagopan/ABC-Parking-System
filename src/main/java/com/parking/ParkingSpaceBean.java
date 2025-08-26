package com.parking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ParkingSpaceBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String address1;
    private String address2;
    private String county;
    private double pricePerHour;
    private boolean accessibleParking;
    private boolean closeToBus;
    private int bulkQuantity = 1;
    private String spaceNumber;   // <-- added from second file

    private ParkingSpace selectedSpace;
    private String selectedSpaceId;
    private double newPrice;

    public static List<ParkingSpace> parkingSpaces = new ArrayList<>();

    public ParkingSpaceBean() {
        pricePerHour = 0.0;
    }

    private void addFacesMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            context.addMessage(null, new FacesMessage(severity, summary, detail));
        }
    }

    private int getNextSpaceNumber(String address1, String county) {
        int max = 0;
        for (ParkingSpace space : parkingSpaces) {
            if (space.getAddress1().equalsIgnoreCase(address1.trim())
                    && space.getCounty().equalsIgnoreCase(county.trim())) {
                try {
                    int num = Integer.parseInt(space.getSpaceNumber());
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return max + 1;
    }

    // Add single parking space
    public String addParkingSpace() {
        try {
            if (address1 == null || address1.trim().isEmpty() ||
                address2 == null || address2.trim().isEmpty() ||
                county == null || county.trim().isEmpty() ||
                pricePerHour < 0.0) {
                addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "All fields are required and price must be positive");
                return null;
            }

            String finalSpaceNumber = (spaceNumber != null && !spaceNumber.trim().isEmpty())
                                        ? spaceNumber.trim()
                                        : String.valueOf(getNextSpaceNumber(address1, county));

            String id = UUID.randomUUID().toString();

            ParkingSpace newSpace = new ParkingSpace(id, finalSpaceNumber, address1.trim(), address2.trim(),
                    county.trim(), pricePerHour, true, accessibleParking, closeToBus);

            parkingSpaces.add(newSpace);

            addFacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    "Parking space #" + finalSpaceNumber + " added successfully");

            clearForm();
            return "parking-list";
        } catch (Exception e) {
            addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Exception: " + e.getMessage());
            return null;
        }
    }

    // Add multiple parking spaces (merged from second file)
    public String addMultipleParkingSpaces() {
        try {
            if (address1 == null || address1.trim().isEmpty() ||
                address2 == null || address2.trim().isEmpty() ||
                county == null || county.trim().isEmpty() ||
                pricePerHour < 0.0) {
                addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "All fields are required and price must be positive");
                return null;
            }

            if (bulkQuantity < 1 || bulkQuantity > 50) {
                addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Bulk quantity must be between 1 and 50");
                return null;
            }

            int startNumber = getNextSpaceNumber(address1, county);

            for (int i = 0; i < bulkQuantity; i++) {
                String finalSpaceNumber = (spaceNumber != null && !spaceNumber.trim().isEmpty())
                                            ? spaceNumber.trim() + "-" + (i + 1)
                                            : String.valueOf(startNumber + i);

                String id = UUID.randomUUID().toString();

                ParkingSpace newSpace = new ParkingSpace(id, finalSpaceNumber, address1.trim(), address2.trim(),
                        county.trim(), pricePerHour, true, accessibleParking, closeToBus);

                parkingSpaces.add(newSpace);
            }

            addFacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    bulkQuantity + " parking space(s) added (from #" + startNumber + " to #" + (startNumber + bulkQuantity - 1) + ")");

            clearForm();
            return "parking-list?faces-redirect=true";
        } catch (Exception e) {
            addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Exception: " + e.getMessage());
            return null;
        }
    }

    private void clearForm() {
        address1 = null;
        address2 = null;
        county = null;
        pricePerHour = 0.0;
        accessibleParking = false;
        closeToBus = false;
        bulkQuantity = 1;
        spaceNumber = null; // reset too
    }

    public String deleteParkingSpace(ParkingSpace space) {
        parkingSpaces.remove(space);
        addFacesMessage(FacesMessage.SEVERITY_INFO, "Deleted",
                "Parking space #" + space.getSpaceNumber() + " deleted successfully");
        return null;
    }

    public String prepareUpdate(ParkingSpace space) {
        this.selectedSpace = space;
        return "update-parking";
    }

    public String updateParkingSpace() {
        if (selectedSpace == null || selectedSpace.getSpaceNumber() == null || selectedSpace.getSpaceNumber().trim().isEmpty()) {
            addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Space number cannot be empty");
            return null;
        }

        for (ParkingSpace space : parkingSpaces) {
            if (space != selectedSpace && space.getSpaceNumber().equals(selectedSpace.getSpaceNumber())) {
                addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Space number already exists");
                return null;
            }
        }

        addFacesMessage(FacesMessage.SEVERITY_INFO, "Updated",
            "Parking space number updated to #" + selectedSpace.getSpaceNumber());

        return "parking-list";
    }

    public void initEditPrice() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            String spaceId = context.getExternalContext().getRequestParameterMap().get("spaceId");
            if (spaceId != null && !spaceId.isEmpty()) {
                selectedSpaceId = spaceId;
                selectedSpace = findParkingSpaceById(spaceId);
                if (selectedSpace != null) {
                    newPrice = selectedSpace.getPricePerHour();
                } else {
                    addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Parking space not found");
                }
            }
        }
    }

    public ParkingSpace findParkingSpaceById(String id) {
        if (id == null) return null;
        for (ParkingSpace space : parkingSpaces) {
            if (id.equals(space.getId())) return space;
        }
        return null;
    }

    public String updatePrice() {
        if (selectedSpace == null) {
            addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No parking space selected");
            return null;
        }
        if (newPrice < 0.0) {
            addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Price must be positive");
            return null;
        }

        selectedSpace.setPricePerHour(newPrice);
        addFacesMessage(FacesMessage.SEVERITY_INFO, "Updated",
                "Price updated to €" + String.format("%.2f", newPrice));

        selectedSpace = null;
        selectedSpaceId = null;
        newPrice = 0.0;
        return "set-prices";
    }

    public String cancelEditPrice() {
        selectedSpace = null;
        selectedSpaceId = null;
        newPrice = 0.0;
        return "set-prices";
    }

    // Getters & Setters
    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }

    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getCounty() { return county; }
    public void setCounty(String county) { this.county = county; }

    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    public boolean isAccessibleParking() { return accessibleParking; }
    public void setAccessibleParking(boolean accessibleParking) { this.accessibleParking = accessibleParking; }

    public boolean isCloseToBus() { return closeToBus; }
    public void setCloseToBus(boolean closeToBus) { this.closeToBus = closeToBus; }

    public List<ParkingSpace> getParkingSpaces() { return parkingSpaces; }

    public ParkingSpace getSelectedSpace() { return selectedSpace; }
    public void setSelectedSpace(ParkingSpace selectedSpace) { this.selectedSpace = selectedSpace; }

    public String getSelectedSpaceId() { return selectedSpaceId; }
    public void setSelectedSpaceId(String selectedSpaceId) { this.selectedSpaceId = selectedSpaceId; }

    public double getNewPrice() { return newPrice; }
    public void setNewPrice(double newPrice) { this.newPrice = newPrice; }

    public int getBulkQuantity() { return bulkQuantity; }
    public void setBulkQuantity(int bulkQuantity) { this.bulkQuantity = bulkQuantity; }

    public String getSpaceNumber() { return spaceNumber; }
    public void setSpaceNumber(String spaceNumber) { this.spaceNumber = spaceNumber; }

    public String getLocation() { return address1; }
    public void setLocation(String location) { this.address1 = location; }

    public double getPrice() { return pricePerHour; }
    public void setPrice(double price) { this.pricePerHour = price; }
}
