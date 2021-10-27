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
        assign(new SeniorWorker(250, 30), singletonList(new BillableZone("Other", fiveToFiveZone())));
        incomeCalculator.calculate(assignments);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignment() {
        assign(new SeniorWorker(250, 30), singletonList(new BillableZone("Wall", fiveToFiveZone())));
        assertBalance(250);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWithBigVendorBonus() {
        assign(new SeniorWorker(250, 30), singletonList(new BillableZone("Wall", fiveToFiveZone())));
        setBigVendorBonusToFirstAssignment();
        assertBalance(175);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneBigWallWithApertureAssignment() {
        assign(new SeniorWorker(250, 30), singletonList(getWallWithApertures()));
        assertBalance(100);
    }

    private BillableZone getWallWithApertures() {
        List<Zone> zones = asList(new Zone(9.0, 5.0), new Zone(9.0, 4.0));
        ZoneWithApertures zoneWithApertures = new ZoneWithApertures(zones);
        return new BillableZone("Wall", new Zone(10.0, 10.0), zoneWithApertures);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneFloorAssignment() {
        assign(new SeniorWorker(180, 30), singletonList(new BillableZone("Floor", fiveToFiveZone())));
        assertBalance(209);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneCeilingAssignment() {
        assign(new SeniorWorker(200, 30), singletonList(new BillableZone("Ceiling", fiveToFiveZone())));
        assertBalance(235);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneSmallCeilingAssignment() {
        assign(new SeniorWorker(200, 30), singletonList(new BillableZone("Ceiling", new Zone(3.0, 5.0))));
        assertBalance(15);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithTwoCeilingAssignment() {
        assign(new SeniorWorker(200, 30), asList(new BillableZone("Ceiling", new Zone(2.0, 5.0)), new BillableZone("Ceiling", new Zone(3.0, 5.0))));
        assertBalance(235);
    }

    @Test
    public void shouldCalculateBalanceWhenOneJuniorWorkerWithOneCeilingAssignment() {
        assign(new JuniorWorker(200, 30), singletonList(new BillableZone("Ceiling", fiveToFiveZone())));
        assertBalance(300);
    }

    @Test
    public void shouldCalculateBalanceWhenOneJuniorWorkerWithOneCeilingAssignmentWithBigVendorBonus() {
        assign(new JuniorWorker(200, 30), singletonList(new BillableZone("Ceiling", fiveToFiveZone())));
        setBigVendorBonusToFirstAssignment();
        assertBalance(250);
    }

    @Test
    public void shouldCalculateBalanceWhenOneJuniorCheapWorkerWithOneCeilingAssignment() {
        assign(new JuniorWorker(100, 30), singletonList(new BillableZone("Ceiling", fiveToFiveZone())));
        assertBalance(400);
    }

    @Test
    public void shouldCalculateBalanceWhenOneSeniorCheapWorkerWithOneCeilingAssignment() {
        assign(new SeniorWorker(180, 30), singletonList(new BillableZone("Ceiling", fiveToFiveZone())));
        assertBalance(259);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWorksTwoDaysButLessAmountThenMaxPerDay() {
        assign(new SeniorWorker(250, 30), singletonList(new BillableZone("Wall", new Zone(9.0, 5.0))));
        assertBalance(450);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWorksTwoDaysAndMoreAmountThenMaxPerDay() {
        assign(new SeniorWorker(250, 30), singletonList(new BillableZone("Wall", new Zone(11.0, 5.0))));
        assertBalance(782.5);
    }

    @Test
    public void shouldCalculateBalanceWhenOneWorkerWithOneWallAssignmentWorksOneDaysButMoreAmountThenMaxPerDay() {
        assign(new SeniorWorker(250, 60), singletonList(new BillableZone("Wall", new Zone(11.0, 5.0))));
        assertBalance(1082.5);
    }

    @Test
    public void shouldCalculateBalanceWhenTwoWorkersWithOneWallAssignment() {
        assign(new SeniorWorker(250, 30), singletonList(new BillableZone("Wall", fiveToFiveZone())));
        assign(new SeniorWorker(250, 30), singletonList(new BillableZone("Wall", fiveToFiveZone())));
        assertBalance(500);
    }

    @Test
    public void shouldCalculateBalanceForComplexTestcase() {
        assign(new SeniorWorker(250, 40), asList(new BillableZone("Floor", new Zone(5.0, 3.0)), new BillableZone("Wall", new Zone(10.0, 10.0))));
        setBigVendorBonusToFirstAssignment();
        Worker worker = new JuniorWorker(200, 30);
        assign(worker, asList(new BillableZone("Ceiling", fiveToFiveZone()), getWallWithApertures()));
        assign(worker, asList(new BillableZone("Ceiling", fiveToFiveZone()), getWallWithApertures()));
        assign(new SeniorWorker(280, 60), asList(new BillableZone("Wall", new Zone(1.0, 5.0)), new BillableZone("Wall", new Zone(11.0, 5.0))));
        assertBalance(4221.5);
    }

    private Zone fiveToFiveZone() {
        return new Zone(5.0, 5.0);
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