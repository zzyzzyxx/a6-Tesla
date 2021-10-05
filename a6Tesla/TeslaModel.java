package a6Tesla;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TeslaModel {
	private YearMonth dateOfSales;
    private Integer numberOfSales;
    // constructor with parsing of String from teslaData array into YearMonth and Integer
    public TeslaModel(String[] teslaData){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yy");
        this.dateOfSales = YearMonth.parse(teslaData[0], formatter);
        this.numberOfSales = Integer.parseInt(teslaData[1]);
    }
    // getters and setters
    public YearMonth getDateOfSales() {
        return dateOfSales;
    }

    public void setDateOfSales(YearMonth dateOfSales) {
        this.dateOfSales = dateOfSales;
    }

    public Integer getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(Integer numberOfSales) {
        this.numberOfSales = numberOfSales;
    }
    // toString method
    @Override
    public String toString() {
        return "{" +
                "dateOfSales=" + dateOfSales +
                ", numberOfSales=" + numberOfSales +
                "}\n";
    }
    // equalto and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeslaModel that = (TeslaModel) o;
        return Objects.equals(dateOfSales, that.dateOfSales) &&
                Objects.equals(numberOfSales, that.numberOfSales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfSales, numberOfSales);
    }
}
