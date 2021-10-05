	package a6Tesla;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.YearMonth;
import java.util.stream.Collectors;
import java.util.*;

public class TeslaService {
	
	String[] teslaData = null;

    BufferedReader reader3 = null;
    BufferedReader readerS = null;
    BufferedReader readerX = null;
    List<TeslaModel> t3Sales = new ArrayList<>();
    List<TeslaModel> tSSales = new ArrayList<>();
    List<TeslaModel> tXSales = new ArrayList<>();

    public void readFile() throws IOException {

        reader3 = new BufferedReader(new FileReader("model3.csv"));
        readerS = new BufferedReader(new FileReader("modelS.csv"));
        readerX = new BufferedReader(new FileReader("modelX.csv"));

        getData(reader3, t3Sales, "Model 3");
        getData(readerS, tSSales, "Model S");
        getData(readerX, tXSales, "Model X");
    }

    public void getData(BufferedReader reader, List<TeslaModel> tSales, String model) throws IOException {
        String line;
        int CSViteration = 0;
        while((line = reader.readLine())!= null) {
                if (CSViteration == 0) {
                    CSViteration++;
                    continue;
                }
                teslaData = line.split(",");
                tSales.add(new TeslaModel(teslaData));
            }
        	
        Optional<YearMonth> maxYearModel = bestMonth(tSales);     
        Optional<YearMonth> minYearModel = worstMonth(tSales);

        Map<Integer, List<TeslaModel>> collectByYear = groupingByYearIntoMap(tSales);
        
        String summingSalesWithinAYear = summingSalesWithinAYear(collectByYear);
        
        printResult(model, maxYearModel, minYearModel, summingSalesWithinAYear);
    }

	private String summingSalesWithinAYear(Map<Integer, List<TeslaModel>> collectByYear) {
		// streaming from the Map above using (entrySet)- getting the Key (year) and sum of sales and collecting them into String   
        String summingSalesWithinAYear = collectByYear.entrySet()
                .stream()														//TeslaModel::getNumberOfSales
                .map(d ->  d.getKey() + " -> " + d.getValue().stream().mapToInt(number -> number.getNumberOfSales()).sum())
                .collect(Collectors.joining("\n"));
		return summingSalesWithinAYear;
	}

	private Map<Integer, List<TeslaModel>> groupingByYearIntoMap(List<TeslaModel> tSales) {
		// streaming and collecting year into Map
        Map<Integer, List<TeslaModel>> collectByYear = tSales.stream()
                											 .collect(Collectors.groupingBy(d -> d.getDateOfSales()
                											 .getYear()));
        //System.out.println(model + ": collectByYear: " + collectByYear);
		return collectByYear;
	}

	private Optional<YearMonth> worstMonth(List<TeslaModel> tSales) {
		// mapping into Optional
        Optional<YearMonth> minYearModel = tSales
                .stream()
                .min(Comparator.comparing(number -> number.getNumberOfSales()))
                .map(date -> date.getDateOfSales());
		return minYearModel;
	}

	private Optional<YearMonth> bestMonth(List<TeslaModel> tSales) {
		// mapping into Optional       	
        Optional<YearMonth> maxYearModel = tSales
                .stream()
                .max(Comparator.comparing(number -> number.getNumberOfSales()))
                .map(date -> date.getDateOfSales());
		return maxYearModel;
	}

	private void printResult(String model, Optional<YearMonth> maxYearModel, Optional<YearMonth> minYearModel,
			String summingSalesWithinAYear) {
		System.out.println(model + " Yearly Sales Report");
        System.out.println("-----------------");
        System.out.println(summingSalesWithinAYear);

        System.out.print("The best month for " + model + " was: ");
        maxYearModel.ifPresent(modl -> System.out.println(modl));
        
        System.out.print("The worst month for " + model +" was: ");
        minYearModel.ifPresent(modl -> System.out.println(modl));
        System.out.println();
	}
}
