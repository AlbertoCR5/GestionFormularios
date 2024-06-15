//package com.example.proyecto.util;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.printing.PDFPageable;
//
//import java.awt.print.PrinterException;
//import java.awt.print.PrinterJob;
//import java.io.File;
//import java.io.IOException;
//
//    public class Imprimir extends Thread{
//
//        @SuppressWarnings("unused")
//        public void imprimirDelegados() {
//
//            try {
//                // Ruta al intérprete de PowerShell
//                String powerShellCmd = "powershell.exe";
//
//                // Ruta al script .ps1 que deseas ejecutar
//                String scriptPath = Constantes.RUTA_ABSOLUTA_HOME.concat(Constantes.RUTA_RELATIVA_DOCUMENTACION_ELECCIONES_HOME.concat(Constantes.IMPRESION_DELEGADOS));
//
//                // Crear el proceso para ejecutar el script
//                ProcessBuilder processBuilder = new ProcessBuilder(powerShellCmd, "-File", scriptPath);
//
//                // Iniciar el proceso
//                Process process = processBuilder.start();
//
//                // Esperar a que el proceso termine
//                int exitCode = process.waitFor();
//
//                // com.example.proyecto.util.Imprimir el código de salida del proceso
//                Thread.sleep(2000);
//                ModificarCamposTextoPDF.mostrarMensaje(("Impresión finalizada.\n"));
//
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @SuppressWarnings("unused")
//        public void imprimirComites() {
//
//            try {
//                // Ruta al intérprete de PowerShell
//                String powerShellCmd = "powershell.exe";
//
//                // Ruta al script .ps1 que deseas ejecutar
//                String scriptPath = Constantes.RUTA_ABSOLUTA_HOME.concat(Constantes.RUTA_RELATIVA_DOCUMENTACION_ELECCIONES_HOME
//                        .concat(Constantes.IMPRESION_COMITE));
//
//                // Crear el proceso para ejecutar el script
//                ProcessBuilder processBuilder = new ProcessBuilder(powerShellCmd, "-File", scriptPath);
//
//                // Iniciar el proceso
//                Process process = processBuilder.start();
//
//                // Esperar a que el proceso termine
//                int exitCode = process.waitFor();
//
//                // com.example.proyecto.util.Imprimir el código de salida del proceso
//                Thread.sleep(2000);
//                System.out.println("Impresión finalizada.\n");
//
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void imprimirCumplimentados() {
//
//            try {
//                // Ruta al intérprete de PowerShell
//                String powerShellCmd = "powershell.exe";
//
//                // Ruta al script .ps1 que deseas ejecutar
//                String scriptPath = Constantes.RUTA_ABSOLUTA_HOME.concat(Constantes.RUTA_RELATIVA_DOCUMENTACION_ELECCIONES_HOME.concat(Constantes.IMPRESION_EMPRESA));
//
//                // Crear el proceso para ejecutar el script
//                ProcessBuilder processBuilder = new ProcessBuilder(powerShellCmd, "-File", scriptPath);
//
//                // Iniciar el proceso
//                Process process = processBuilder.start();
//
//                // Esperar a que el proceso termine
//                int exitCode = process.waitFor();
//
//                // com.example.proyecto.util.Imprimir el código de salida del proceso
//                Thread.sleep(2000);
//                System.out.println("Impresión finalizada.\n");
//
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void imprimirDirectamente(String rutaArchivoPDF) {
//
//            try {
//                // Cargar el documento PDF
//                PDDocument document = PDDocument.load(new File(rutaArchivoPDF));
//
//                // Configurar la impresión
//                PrinterJob job = PrinterJob.getPrinterJob();
//                job.setPageable(new PDFPageable(document));
//
//                // Mostrar el cuadro de diálogo de impresión
//                if (job.printDialog()) {
//                    // com.example.proyecto.util.Imprimir el documento
//                    job.print();
//                }
//
//                // Cerrar el documento después de imprimir
//                document.close();
//
//            } catch (IOException | PrinterException e) {
//                e.printStackTrace();
//            }
//        }
//    }
