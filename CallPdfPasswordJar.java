import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CallPdfPasswordJar {

    /**
     * Copy source PDF to target, then apply password to the copied file.
     *
     * @param jarPath         full path to GUMinimalPdfPassword.jar
     * @param pdfSourcePath  original PDF (never modified)
     * @param pdfTargetPath  copied PDF (password applied here)
     * @param password       password to apply
     * @return exit code (0 = success)
     */
    public static int addPassword(
            String jarPath,
            String pdfSourcePath,
            String pdfTargetPath,
            String password) throws Exception {

        // 1. COPY source → target (unprotected)
        Path source = Paths.get(pdfSourcePath);
        Path target = Paths.get(pdfTargetPath);

        if (target.getParent() != null) {
            Files.createDirectories(target.getParent());
        }

        Files.copy(
            source,
            target,
            StandardCopyOption.REPLACE_EXISTING
        );

        // 2. Run password JAR on TARGET file only
        String javaExe =
            System.getProperty("java.home") +
            File.separator + "bin" +
            File.separator + "java";

        ProcessBuilder pb = new ProcessBuilder(
            javaExe,
            "-jar",
            jarPath,
            pdfTargetPath,   // ✅ encrypt the copied file
            password
        );

        pb.directory(new File(jarPath).getParentFile());
        pb.redirectErrorStream(true);

        Process process = pb.start();

        BufferedReader reader =
            new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        return process.waitFor();
    }

    // Local test runner
    public static void main(String[] args) throws Exception {

        if (args.length < 3) {
            throw new IllegalArgumentException(
                "Usage: java CallPdfPasswordJar <input.pdf> <password> <output.pdf>"
            );
        }

        String baseDir = "C:\\Java\\pdf_git";

        String sourcePdf = baseDir + "\\" + args[0];
        String password  = args[1];
        String targetPdf = baseDir + "\\" + args[2];

        int rc = addPassword(
            baseDir + "\\MinimalPdfPassword.jar",
            sourcePdf,
            targetPdf,
            password
        );

        if (rc != 0) {
            throw new RuntimeException(
                "PDF password jar failed. Exit code " + rc
            );
        }

        System.out.println("PDF copied and password applied successfully");
    }
}
