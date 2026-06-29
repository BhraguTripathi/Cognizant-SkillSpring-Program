package DesignPattern;

public class FactoryMethodPatternExample {
    public static void main(String[] args) {
        DocumentFactory wordFactory = new WordDocumentFactory();
        Document wordDoc = wordFactory.openNewDocument();
        wordDoc.save();

        System.out.println();

        DocumentFactory pdfFactory = new PdfDocumentFactory();
        Document pdfDoc = pdfFactory.openNewDocument();
        pdfDoc.save();

        System.out.println();

        DocumentFactory excelFactory = new ExcelDocumentFactory();
        Document excelDoc = excelFactory.openNewDocument();
        excelDoc.save();

        System.out.println();
        System.out.println("--- Dynamic creation based on a 'type' string ---");
        createAndUse("pdf");
        createAndUse("word");
    }

    static void createAndUse(String type) {
        DocumentFactory factory = switch (type.toLowerCase()) {
            case "word" -> new WordDocumentFactory();
            case "pdf" -> new PdfDocumentFactory();
            case "excel" -> new ExcelDocumentFactory();
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };

        Document doc = factory.openNewDocument();
        doc.save();
    }
}

interface Document {
    void open();

    void save();
}

class WordDocument implements Document {
    public void open() {
        System.out.println("Opening a Word document (.docx)...");
    }

    public void save() {
        System.out.println("Saving Word document...");
    }
}

class PdfDocument implements Document {
    public void open() {
        System.out.println("Opening a PDF document (.pdf)...");
    }

    public void save() {
        System.out.println("Saving PDF document...");
    }
}

class ExcelDocument implements Document {
    public void open() {
        System.out.println("Opening an Excel document (.xlsx)...");
    }

    public void save() {
        System.out.println("Saving Excel document...");
    }
}

abstract class DocumentFactory {
    public abstract Document createDocument();

    public Document openNewDocument() {
        Document doc = createDocument();
        doc.open();
        return doc;
    }
}

class WordDocumentFactory extends DocumentFactory {
    public Document createDocument() {
        return new WordDocument();
    }
}

class PdfDocumentFactory extends DocumentFactory {
    public Document createDocument() {
        return new PdfDocument();
    }
}

class ExcelDocumentFactory extends DocumentFactory {
    public Document createDocument() {
        return new ExcelDocument();
    }
}
