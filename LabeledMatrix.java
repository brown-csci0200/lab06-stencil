import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LabeledMatrix {
    double[][] values;
    List<String> rowLabels;
    List<String> colLabels;

    public LabeledMatrix(List<String> rowLabels, List<String> columnLabels) {
        this.rowLabels = rowLabels;
        this.colLabels = columnLabels;
        this.values = new double[rowLabels.size()][columnLabels.size()];
    }

    public LabeledMatrix(List<String> rowLabels, List<String> columnLabels, double... vals) {
        this.rowLabels = rowLabels;
        this.colLabels = columnLabels;
        this.values = new double[rowLabels.size()][columnLabels.size()];
        int nCols = columnLabels.size();
        int totalSize = rowLabels.size() * columnLabels.size();
        if (vals.length != totalSize) {
            throw new RuntimeException(String.format("Labeled Matrix expected %d values but %d were given. Check to make sure you inputted the same number of values as the number of rows * the number of columns.", totalSize, vals.length));
        }

        for (int i = 0; i < vals.length; i++) {
            double val = vals[i];
            int r = i / nCols;
            int c = i % nCols;
            this.values[r][c] = val;
        }
    }

    // using a csv file, but providing labels themselves
    public LabeledMatrix(List<String> rowLabels, List<String> columnLabels, String filename) {
        this.rowLabels = rowLabels;
        this.colLabels = columnLabels;
        this.values = new double[rowLabels.size()][columnLabels.size()];

        String line;
        String delimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // first line, populate colLabels
            int i = 0;
            while ((line = br.readLine()) != null) {
                // check the number of rows
                if (i == this.values.length) {
                    throw new RuntimeException(String.format("Too many rows in the CSV file %s", filename));
                }

                String[] vals = line.split(delimiter);

                // check the number of cols
                if (vals.length != this.values[0].length) {
                    throw new RuntimeException(String.format("Wrong number of columns in row %d in the CSV file %s", i + 1, filename));
                }
                for (int j = 0; j < vals.length; j++){
                    this.values[i][j] = Double.parseDouble(vals[j]);
                }
                i++;
            }
            // not enough rows from parsing
            if (i < this.values.length) {
                throw new RuntimeException(String.format("Not enough rows in the CSV file %s", filename));
            }
        } catch (IOException e) {
            System.out.println("Error during initializing the matrix from a CSV file: ");
            e.printStackTrace();
        }
    }

    public void setVal(int row, int col, double value) {
        this.values[row][col] = value;
    }

    public double getVal(int row, int col) {
        return this.values[row][col];
    }

    public int getRows() {
        return this.rowLabels.size();
    }

    public int getCols() {
        return this.colLabels.size();
    }

    public void setRow(int i, double[] vals) {
        if (vals.length != this.values[i].length) {
            throw new RuntimeException("Length of the matrix row and argument row are not the same");
        }

        for (int j = 0; j < this.values[i].length; j++) {
            this.values[i][j] = vals[j];
        }
    }

    public void setCol(int j, double[] vals) {
        if (vals.length != this.values.length) {
            throw new RuntimeException("Length of the matrix column and argument column are not the same");
        }

        for (int i = 0; i < this.values.length; i++) {
            this.values[i][j] = vals[i];
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Add row labels
        sb.append("RowLabels: ");
        sb.append(String.join(", ", rowLabels));
        sb.append("\n");

        // Add column labels
        sb.append("ColLabels: ");
        sb.append(String.join(", ", colLabels));
        sb.append("\n");

        // Add matrix values
        for (int i = 0; i < values.length; i++) {
//            sb.append(this.rowLabels.get(i));
//            sb.append(": ");
            for (int j = 0; j < values[i].length; j++) {
                sb.append(String.format("%.2f", values[i][j]));
                if (j < values[i].length - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LabeledMatrix)) {
            return false;
        }
        LabeledMatrix other = (LabeledMatrix) o;

        if (this.getRows() != other.getRows() || this.getCols() != other.getCols()) {
            return false;
        }

        //comparing double numbers
        double tolerance = 1e-9;

        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++) {
                if (Math.abs(this.values[i][j] - other.values[i][j]) > tolerance) {
                    return false;
                }
            }
        }

        return true;
    }

    public static LabeledMatrix multiplyMatrices(LabeledMatrix a, LabeledMatrix b) {
        /***
         * Returns the result of multiplying matrix a with matrix b
         */
        if (a.getCols() != b.getRows()) {
            throw new IllegalArgumentException("Matrix a needs to have as many columns as matrix b has rows.");
        }

        LabeledMatrix res = new LabeledMatrix(a.rowLabels, b.colLabels);

        for (int row_a = 0; row_a < a.getRows(); row_a++) {
            for (int col_b = 0; col_b < b.getCols(); col_b++) {
                double dot_prod = 0;
                for (int i = 0; i < a.getCols(); i++) {
                    dot_prod += a.getVal(row_a, i) * b.getVal(i, col_b);
                }
                res.setVal(row_a, col_b, dot_prod);
            }
        }

        return res;
    }
}
