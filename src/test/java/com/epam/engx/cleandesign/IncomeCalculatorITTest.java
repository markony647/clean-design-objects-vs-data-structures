package com.epam.engx.cleandesign;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class IncomeCalculatorITTest {

    private static final double DELTA = 0.001;

    private IncomeCalculator incomeCalculator;
    private final List<Assignment> assignments = new ArrayList<>();

    @Before
    public void setUp() {
        HashMap<String, Double> zoneTypeWorkPrice = new HashMap<>();
        zoneTypeWorkPrice.put("Wall", 15.0);
        zoneTypeWorkPrice.put("Floor", 10.0);
        zoneTypeWorkPrice.put("Ceiling", 12.0);
        MaterialPriceCalculator materialPriceCalculator = new MaterialPriceCalculator();
        WorkPriceCalculator workPriceCalculator = new WorkPriceCalculator(zoneTypeWorkPrice);
        RevenueCalculator revenueCalculator = new RevenueCalculator(workPriceCalculator, materialPriceCalculator);
        ExpensesCalculator expensesCalculator = new ExpensesCalculator();
        incomeCalculator = new IncomeCalculator(revenueCalculator, expensesCalculator);
    }

    @Test
    public void shouldCalculateZeroBalanceWhenNoAssignments() {
        assertBalance(0.0);
    }

    @Test(expected = WrongZoneTypeException.class)
    public void shouldThrowExceptionWhenZoneWithWrongType() {
        assign(new Worker(250, 30), singletonList(new BillableZone("Other", 5.0, 5.0)));
        incomeCalculator.calculate(assignments);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignment() {
        assign(new Worker(250, 30), singletonList(new BillableZone("Wall", 5.0, 5.0)));
        assertBalance(250);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWithBigVendorBonus() {
        assign(new Worker(250, 30), singletonList(new BillableZone("Wall", 5.0, 5.0)));
        setBigVendorBonusToFirstAssignment();
        assertBalance(175);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneBigWallWithApertureAssignment() {
        assign(new Worker(250, 30), singletonList(getWallWithApertures()));
        assertBalance(100);
    }

    private BillableZone getWallWithApertures() {
        List<Zone> zones = asList(new Zone(9.0, 5.0), new Zone(9.0, 4.0));
        ZoneWithApertures zoneWithApertures = new ZoneWithApertures(zones);
        return new BillableZone("Wall", 10.0, 10.0, zoneWithApertures);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneFloorAssignment() {
        assign(new Worker(180, 30), singletonList(new BillableZone("Floor", 5.0, 5.0)));
        assertBalance(209);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneCeilingAssignment() {
        assign(new Worker(200, 30), singletonList(new BillableZone("Ceiling", 5.0, 5.0)));
        assertBalance(235);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneSmallCeilingAssignment() {
        assign(new Worker(200, 30), singletonList(new BillableZone("Ceiling", 3.0, 5.0)));
        assertBalance(15);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithTwoCeilingAssignment() {
        assign(new Worker(200, 30), asList(new BillableZone("Ceiling", 2.0, 5.0), new BillableZone("Ceiling", 3.0, 5.0)));
        assertBalance(235);
    }

    @Test
    public void shouldCalculateBalanceWhenOneJuniorWorkerWithOneCeilingAssignment() {
        assign(new Worker(200, 30, true), singletonList(new BillableZone("Ceiling", 5.0, 5.0)));
        assertBalance(300);
    }

    @Test
    public void shouldCalculateBalanceWhenOneJuniorWorkerWithOneCeilingAssignmentWithBigVendorBonus() {
        assign(new Worker(200, 30, true), singletonList(new BillableZone("Ceiling", 5.0, 5.0)));
        setBigVendorBonusToFirstAssignment();
        assertBalance(250);
    }

    @Test
    public void shouldCalculateBalanceWhenOneJuniorCheapWorkerWithOneCeilingAssignment() {
        assign(new Worker(100, 30, true), singletonList(new BillableZone("Ceiling", 5.0, 5.0)));
        assertBalance(400);
    }

    @Test
    public void shouldCalculateBalanceWhenOneSeniorCheapWorkerWithOneCeilingAssignment() {
        assign(new Worker(180, 30), singletonList(new BillableZone("Ceiling", 5.0, 5.0)));
        assertBalance(259);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWorksTwoDaysButLessAmountThenMaxPerDay() {
        assign(new Worker(250, 30), singletonList(new BillableZone("Wall", 9.0, 5.0)));
        assertBalance(450);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWorksTwoDaysAndMoreAmountThenMaxPerDay() {
        assign(new Worker(250, 30), singletonList(new BillableZone("Wall", 11.0, 5.0)));
        assertBalance(782.5);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWorksOneDaysButMoreAmountThenMaxPerDay() {
        assign(new Worker(250, 60), singletonList(new BillableZone("Wall", 11.0, 5.0)));
        assertBalance(1082.5);
    }

    @Test
    public void shouldCalculateBalanceWhenTwoWorkersWithOneWallAssignment() {
        assign(new Worker(250, 30), singletonList(new BillableZone("Wall", 5.0, 5.0)));
        assign(new Worker(250, 30), singletonList(new BillableZone("Wall", 5.0, 5.0)));
        assertBalance(500);
    }

    @Test
    public void shouldCalculateBalanceForComplexTestcase() {
        assign(new Worker(250, 40), asList(new BillableZone("Floor", 5.0, 3.0), new BillableZone("Wall", 10.0, 10.0)));
        setBigVendorBonusToFirstAssignment();
        Worker worker = new Worker(200, 30, true);
        assign(worker, asList(new BillableZone("Ceiling", 5.0, 5.0), getWallWithApertures()));
        assign(worker, asList(new BillableZone("Ceiling", 5.0, 5.0), getWallWithApertures()));
        assign(new Worker(280, 60), asList(new BillableZone("Wall", 1.0, 5.0), new BillableZone("Wall", 11.0, 5.0)));
        assertBalance(4221.5);
    }

    private void setBigVendorBonusToFirstAssignment() {
        assignments.get(0).setVendorBonus(100);
    }

    private void assign(Worker worker, List<BillableZone> billableZones) {
        Assignment assignment = new Assignment(worker, billableZones, 50);
        assignments.add(assignment);
    }

    private void assertBalance(double expected) {
        assertEquals(expected, incomeCalculator.calculate(assignments), DELTA);
    }

}