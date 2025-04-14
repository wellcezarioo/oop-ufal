package Entities;

public class Company extends TaxPayer {
    private int employees;

    public Company(String name, double anualIncome, int employees){
        super(name, anualIncome);
        this.employees = employees;
    }

    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    @Override
    public double calculateTaxes() {
        if(employees < 10){
            return anualIncome * 0.16;
        }

        return anualIncome * 0.14;
    }
}
