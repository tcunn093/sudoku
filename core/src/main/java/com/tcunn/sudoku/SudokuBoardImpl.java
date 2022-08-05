package com.tcunn.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.tcunn.sudoku.game.board.Board;
import com.tcunn.sudoku.game.board.InvalidMutationException;
import com.tcunn.sudoku.game.board.Validator;

public class SudokuBoardImpl implements Board<Integer, Entry<Integer, Integer>>, Validator{

    private static final Integer DEFAULT_SECTOR_LENGTH = 3;

    private static final Integer MINIMUM_VALUE = 1;

    private List<List<Integer>> boardData;

    private final int sectorLength;

    public SudokuBoardImpl(){
        this(DEFAULT_SECTOR_LENGTH);
    }

    public SudokuBoardImpl(int sectorLength){
        this.sectorLength = sectorLength;
        reset();
    }

    private static boolean isPerfectSquare(int x)
    {
        if (x >= 0) {
            int sr = (int) Math.sqrt(x);
            return ((sr * sr) == x);
        }
        return false;
    }

    public SudokuBoardImpl(List<List<Integer>> boardData){
        
        int length = boardData.size();

        for(List<Integer> row: boardData){
            if(row.size() != length){
                throw new IllegalArgumentException("Provided board does not have equal row and column count.");
            }
        }

        if(!isPerfectSquare(length)){
            throw new IllegalArgumentException("Provided board side length is not a perfect square (4, 9, 25, 36, etc...)");
        }

        this.sectorLength = (int) Math.sqrt(length);
        this.boardData = boardData;

    }

    private boolean isSolved(List<List<Integer>> boardData){
        return !hasNulls(boardData) && validate(boardData);
    }

    public boolean isSolved(){
        return isSolved(this.boardData);
    }

    public List<List<Integer>> getBoardData() {
        return boardData;
    }

    private void setBoardData(List<List<Integer>> boardData) {
        this.boardData = boardData;
    }

    @Override
    public void reset() {
        this.boardData = generateEmptyMatrix(this.getSideLength());
    }

    private List<List<Integer>> generateEmptyMatrix(int size){
        List<List<Integer>> matrix = new ArrayList<>(size);

        for(int i = 0; i < size; i++){

            List<Integer> column = new ArrayList<>(size);

            for(int j = 0; j < size; j++){
                column.add(null);
            }

            matrix.add(column);

        }

        return matrix;
    }

    @Override
    public void initialise() {
        setBoardData(populate());
    }

    public boolean isSolvable(List<List<Integer>> board){

        List<List<Integer>> boardDataClone = new ArrayList<>();

        for(List<Integer> row: board){
            boardDataClone.add(new ArrayList<>(row));
        }
    
        return populate(boardDataClone);
    }

    public void makeSolvable(){
        setBoardData(removeRandomValues(this.boardData));
    }

    public List<List<Integer>> removeRandomValues(List<List<Integer>> board){

        boolean valid = false;
        List<List<Integer>> boardDataClone = new ArrayList<>();
        while(!valid){
            boardDataClone = new ArrayList<>();

            for(List<Integer> row: board){
                boardDataClone.add(new ArrayList<>(row));
            }
    
            int totalSquares = this.getSideLength() * this.getSideLength();
            int squaresToRemove = totalSquares - (this.getSideLength() * 2 - 1);
    
            while(squaresToRemove > 0){
                boardDataClone
                .get(ThreadLocalRandom.current().nextInt(0, this.getMaximumValue()))
                .set(ThreadLocalRandom.current().nextInt(0, this.getMaximumValue()), null);
                squaresToRemove--;
            }

            valid = isSolvable(boardDataClone);
        }

        return boardDataClone;

    }

    private List<List<Integer>> populate(){
        List<List<Integer>> board = generateEmptyMatrix(this.getSideLength());
        populateDiagonal(board);
        populate(board);
        return board;
    }

    private List<List<Integer>> populateDiagonal(List<List<Integer>> board){
        // diagonal sectors are mutually exclusive and any permutation of values in our range is valid
        for(int i = 0; i < this.getSideLength(); i += this.sectorLength){

            List<List<Integer>> sector = generateSector(this.sectorLength);

            for(int j = i; j < this.sectorLength + i; j++){
                for(int k = 0; k < this.sectorLength; k++){
                    int sectorValue = sector.get(j - i).get(k);
                    board.get(j).set(k + i, sectorValue);
                }
            }
        }

        return board;
    }

    private boolean hasNulls(List<List<Integer>> board){

        boolean foundNull = false;

        for(int i = 0; i < board.size(); i++){
            for(int j = 0; j < board.size(); j++){
                foundNull = board.get(i).get(j) == null;
                if(foundNull){
                    break;
                }
            }
            if(foundNull){
                break;
            }

        }

        return foundNull;
    }

    private boolean populate(List<List<Integer>> board){

        int i = 0;
        int j = 0;
        boolean foundNull = false;

        for(i = 0; i < board.size(); i++){
            for(j = 0; j < board.size(); j++){
                foundNull = board.get(i).get(j) == null;
                if(foundNull){
                    break;
                }
            }
            if(foundNull){
                break;
            }

        }

        // No nulls are left in the matrix. Terminate recursion.
        if( i == board.size() && j == board.size()){
            return true;
        }

        List<Integer> row = getRow(board, i);
        List<Integer> column = getColumn(board, j);
        List<Integer> sector = getSector(board, i , j);

        Set<Integer> usedValues = new HashSet<>();
        usedValues.addAll(row);
        usedValues.addAll(column);
        usedValues.addAll(sector);

        if(usedValues.contains(null)){
            usedValues.remove(null);
        }

        List<Integer> remaining = this.getRemainingValidValues(new ArrayList<>(usedValues));

        for(Integer value: remaining){

            board.get(i).set(j, value);

            if(populate(board)){
                return true;
            }

            board.get(i).set(j, null);

        }

        return false;
    }

    private int getSideLength(){
        return this.sectorLength*this.sectorLength;
    }

    private int getMaximumValue(){
        return this.getSideLength();
    }

    private void print(List<List<Integer>> board){
        for(int i = 0; i < board.size(); i++){
            System.out.println(getRow(board, i));
        }
        System.out.println();
    }

    public void print(){
        print(this.boardData);
    }

    @Override
    public void mutate(Integer value, Entry<Integer, Integer> position) throws InvalidMutationException {

        if(!checkIfValueInValidRange(value)){
            throw new InvalidMutationException("Value " + value + " is not within the allowable range. Allowable range: ("+ MINIMUM_VALUE +"-" + this.getMaximumValue() + ")");
        }

        int x = position.getKey();
        int y = position.getValue();

        if(x < 0 || x >= this.getSideLength()){
            throw new IllegalArgumentException("x coordinate not in valid range");
        }

        if(y < 0 || y >= this.getSideLength()){
            throw new IllegalArgumentException("y coordinate not in valid range");
        }

        this.boardData.get(x).set(y, value);
        
    }

    private boolean checkIfValueInValidRange(int value){
        return value <= this.getMaximumValue() && value >= MINIMUM_VALUE;
    }

    private List<Integer> getRemainingValidValues(List<Integer> values){

        List<Integer> remaining = new ArrayList<>();

        for(int i = MINIMUM_VALUE; i <= this.getMaximumValue(); i++){
            if(!values.contains(i)){
                remaining.add(i);
            }
        }

        return remaining;

    }

    private boolean hasAllUniqueValues(List<Integer> values){
        Set<Integer> unique = new HashSet<>(values);
        return unique.size() == values.size();
    }

    private boolean checkValues(List<Integer> values){

        for(int j = 0; j < values.size(); j++){
            Integer value = values.get(j);

            if(value == null){
                continue;
            }

            if(!checkIfValueInValidRange(value)){
                return false;
            }

        }
        // filter nulls before we check for uniqueness
        if(!hasAllUniqueValues(values.stream().filter(value -> value != null).collect(Collectors.toList()))){
            return false;
        }

        return true;
    }

    private boolean checkColumns(List<List<Integer>> board){

        for(int i = 0; i < this.getSideLength(); i++){

            if(!checkValues(getColumn(board, i))){
                return false;
            }

        }

        return true;
    }

    private boolean checkRows(List<List<Integer>> board){
        for(int i = 0; i < this.getSideLength(); i++){

            if(!checkValues(getRow(board, i))){
                return false;
            }

        }

        return true;
    }

    private boolean checkSectors(List<List<Integer>> board){

        for(int i = 0; i < this.getSideLength(); i++){

            for(int j = 0; j < this.getSideLength(); j++){

                if(!checkValues(getSector(board, i, j))){
                    return false;
                }
    
            }

        }

        return true;

    }

    private List<Integer> getSector(List<List<Integer>> boardData, int x, int y){
        List<Integer> sector = new ArrayList<>();

        int sectorX = (x / this.sectorLength) * this.sectorLength;
        int sectorY = (y / this.sectorLength) * this.sectorLength;

        for(int i = 0; i < sectorLength; i++){
            for(int j = 0; j < sectorLength; j++){
                sector.add(boardData.get(sectorX + i).get(sectorY + j));
            }
        }

        return sector;
    }

    private List<Integer> getRow(List<List<Integer>> boardData, int x){
        return boardData.get(x);
    }

    private List<Integer> getColumn(List<List<Integer>> boardData, int y){
        List<Integer> col = new ArrayList<>();

        for(int i = 0; i < boardData.size(); i++){
            col.add(boardData.get(i).get(y));
        }

        return col;
    }

    private List<Integer> generateListOfValidValues(){

        List<Integer> list = new ArrayList<>();

        for(int i = MINIMUM_VALUE; i <= this.getSideLength(); i++){
            list.add(i);
        }

        Collections.shuffle(list);

        return list;
    }

    private List<List<Integer>> generateSector(int sectorLength){
        Queue<Integer> values = new LinkedList<>(generateListOfValidValues());

        List<List<Integer>> sector = generateEmptyMatrix(sectorLength);

        for(int i = 0; i < sector.size(); i++){
            for(int j = 0; j < sector.size(); j++){
                sector.get(i).set(j, values.poll());
            }
        }

        return sector;
    }

    private boolean validate(List<List<Integer>> board){

        boolean valid = checkColumns(board);

        if(valid){
            valid = checkRows(board);
        }

        if(valid){
            checkSectors(board);
        }
        
        return valid;

    }

    @Override
    public boolean validate() {
        return validate(this.boardData);
    }
    
}
