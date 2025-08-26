package com.parking;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@ManagedBean(name = "searchParkingBean")
@ViewScoped
public class SearchParkingBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // Search criteria fields
    private String addressSearch;
    private String countySearch;
    private String maxPrice;

    // Feature checkboxes bound from XHTML
    private boolean accessibleOnly;
    private boolean busOnly;

    // Search results (using List instead of ArrayList)
    private List<ParkingSpace> searchResults;
    private boolean searchPerformed = false;

    // Inject the existing parking space bean
    @ManagedProperty(value = "#{parkingSpaceBean}")
    private ParkingSpaceBean parkingSpaceBean;

    // Constructors
    public SearchParkingBean() {
        this.searchResults = new ArrayList<>();
    }

    // Existing search method (keeps original behavior)
    public String searchParkingSpaces() {
        try {
            List<ParkingSpace> allSpaces = parkingSpaceBean.getParkingSpaces();

            if (allSpaces == null || allSpaces.isEmpty()) {
                addFacesMessage(FacesMessage.SEVERITY_INFO, "No Data", "No parking spaces available to search.");
                this.searchResults = new ArrayList<>();
                this.searchPerformed = true;
                return null;
            }

            // Start with all spaces
            List<ParkingSpace> filteredSpaces = new ArrayList<>(allSpaces);

            // Filter by address if provided (searches both address1 and address2)
            if (addressSearch != null && !addressSearch.trim().isEmpty()) {
                String searchTerm = addressSearch.toLowerCase().trim();
                filteredSpaces = filteredSpaces.stream().filter(space -> (space.getAddress1() != null
                        && space.getAddress1().toLowerCase().contains(searchTerm))
                        || (space.getAddress2() != null && space.getAddress2().toLowerCase().contains(searchTerm)))
                        .collect(Collectors.toList());
            }

            // Filter by county if provided
            if (countySearch != null && !countySearch.trim().isEmpty()) {
                filteredSpaces = filteredSpaces.stream()
                        .filter(space -> space.getCounty() != null
                                && space.getCounty().toLowerCase().contains(countySearch.toLowerCase().trim()))
                        .collect(Collectors.toList());
            }

            // Filter by maximum price if provided
            if (maxPrice != null && !maxPrice.trim().isEmpty()) {
                try {
                    double maxPriceValue = Double.parseDouble(maxPrice.trim());
                    filteredSpaces = filteredSpaces.stream().filter(space -> space.getPricePerHour() <= maxPriceValue)
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                            "Invalid price format. Please enter a valid number.");
                    return null;
                }
            }

            // Filter by accessibility if requested
            if (accessibleOnly) {
                filteredSpaces = filteredSpaces.stream().filter(space -> space.isAccessibleParking())
                        .collect(Collectors.toList());
            }

            // Filter by proximity to bus if requested
            if (busOnly) {
                filteredSpaces = filteredSpaces.stream().filter(space -> space.isCloseToBus())
                        .collect(Collectors.toList());
            }

            this.searchResults = filteredSpaces;
            this.searchPerformed = true;

            if (searchResults.isEmpty()) {
                addFacesMessage(FacesMessage.SEVERITY_INFO, "No Results",
                        "No parking spaces found matching your search criteria.");
            } else {
                addFacesMessage(FacesMessage.SEVERITY_INFO, "Search Complete",
                        "Found " + searchResults.size() + " parking space(s) matching your criteria.");
            }

        } catch (Exception e) {
            addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "An error occurred while searching: " + e.getMessage());
            this.searchResults = new ArrayList<>();
        }

        return null; // Stay on the same page
    }

    // Convenience method so existing XHTML
    // action="#{searchParkingBean.searchAndRedirect}" works without changing the page
    public String searchAndRedirect() {
        searchParkingSpaces();
        return null;
    }

    // Clear search results and form
    public String clearSearch() {
        this.addressSearch = null;
        this.countySearch = null;
        this.maxPrice = null;
        this.accessibleOnly = false;
        this.busOnly = false;
        this.searchResults = new ArrayList<>();
        this.searchPerformed = false;
        return null;
    }

    // Helper method to add faces message (matches your existing pattern)
    private void addFacesMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            context.addMessage(null, new FacesMessage(severity, summary, detail));
        }
    }

    // Check if any search criteria is provided
    public boolean hasSearchCriteria() {
        return (addressSearch != null && !addressSearch.trim().isEmpty())
                || (countySearch != null && !countySearch.trim().isEmpty())
                || (maxPrice != null && !maxPrice.trim().isEmpty()) || accessibleOnly || busOnly;
    }

    // Get available spaces from search results
    public List<ParkingSpace> getAvailableSearchResults() {
        return searchResults.stream().filter(ParkingSpace::isAvailable)
                .collect(Collectors.toList());
    }

    // Get occupied spaces from search results
    public List<ParkingSpace> getOccupiedSearchResults() {
        return searchResults.stream().filter(space -> !space.isAvailable())
                .collect(Collectors.toList());
    }

    // Get count of available results
    public long getAvailableResultsCount() {
        return searchResults.stream().filter(ParkingSpace::isAvailable).count();
    }

    // Get count of occupied results
    public long getOccupiedResultsCount() {
        return searchResults.stream().filter(space -> !space.isAvailable()).count();
    }

    // Getters and Setters
    public String getAddressSearch() {
        return addressSearch;
    }

    public void setAddressSearch(String addressSearch) {
        this.addressSearch = addressSearch;
    }

    public String getCountySearch() {
        return countySearch;
    }

    public void setCountySearch(String countySearch) {
        this.countySearch = countySearch;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<ParkingSpace> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<ParkingSpace> searchResults) {
        this.searchResults = searchResults;
    }

    public boolean isSearchPerformed() {
        return searchPerformed;
    }

    public void setSearchPerformed(boolean searchPerformed) {
        this.searchPerformed = searchPerformed;
    }

    public ParkingSpaceBean getParkingSpaceBean() {
        return parkingSpaceBean;
    }

    public void setParkingSpaceBean(ParkingSpaceBean parkingSpaceBean) {
        this.parkingSpaceBean = parkingSpaceBean;
    }

    // New getters/setters for the feature checkboxes used in XHTML
    public boolean isAccessibleOnly() {
        return accessibleOnly;
    }

    public void setAccessibleOnly(boolean accessibleOnly) {
        this.accessibleOnly = accessibleOnly;
    }

    public boolean isBusOnly() {
        return busOnly;
    }

    public void setBusOnly(boolean busOnly) {
        this.busOnly = busOnly;
    }

    // Backward compatibility methods (if needed)
    public String getLocationInfo() {
        return addressSearch;
    }

    public void setLocationInfo(String locationInfo) {
        this.addressSearch = locationInfo;
    }

    public String getPriceRange() {
        return maxPrice;
    }

    public void setPriceRange(String priceRange) {
        this.maxPrice = priceRange;
    }
}
