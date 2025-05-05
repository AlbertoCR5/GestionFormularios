package com.example.proyecto.main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VCardGenerator {

    public static void main(String[] args) {
        String[] contacts = {
                "Correo: lorcavillar@hotmail.com,Empresa: Precon Molins Precast Solutions,CIF: A28135358,Dirección Empresa: Ctra. Sevilla-Cádiz 552,Municipio Empresa: Dos Hermanas,Código Postal Empresa: 41704,Convenio: Derivado del Cemento,Nombre: Isabel Maria,Apellidos: Lorca Villar,DNI: 52669628Q,Dirección: Avenida cruz roja 16,Localidad: Dos Hermanas,Código Postal: 41701,Teléfono: 654668294,Delegado de Prevención: No,Afiliado: No,Sector: Construcción y Minería,WhatsApp: https://chat.whatsapp.com/FaUlzvzaPswHX0mBdb8X7x,Imagen: C:\\Users\\VORPC\\Pictures\\Saved Pictures\\Precon Molins.jpg",
                "Correo: raul.lobato@molins.es,Empresa: Precon Molins Precast Solutions,CIF: A28135359,Dirección Empresa: Ctra. Sevilla-Cádiz 552,Municipio Empresa: Dos Hermanas,Código Postal Empresa: 41704,Convenio: Derivado del Cemento,Nombre: Raúl,Apellidos: Lobato Ruiz,DNI: 32088473T,Dirección: Av. Felipe González Márquez 10 Bloque 10 4C,Localidad: Dos Hermanas,Código Postal: 41704,Teléfono: 622302103,Delegado de Prevención: No,Afiliado: No,Sector: Construcción y Minería,WhatsApp: https://chat.whatsapp.com/FaUlzvzaPswHX0mBdb8X7x,Imagen: C:\\Users\\VORPC\\Pictures\\Saved Pictures\\Precon Molins.jpg",
                "Correo: dhabernau@gmail.com,Empresa: Precon Molins Precast Solutions,CIF: A28135360,Dirección Empresa: Ctra. Sevilla-Cádiz 552,Municipio Empresa: Dos Hermanas,Código Postal Empresa: 41704,Convenio: Derivado del Cemento,Nombre: Dionisio,Apellidos: Habernau Romero,DNI: 52666372Y,Dirección: La Jimena 2,Localidad: Dos Hermanas,Código Postal: 41703,Teléfono: 600399603,Delegado de Prevención: Sí,Afiliado: Si,Sector: Construcción y Minería,WhatsApp: https://chat.whatsapp.com/FaUlzvzaPswHX0mBdb8X7x,Imagen: C:\\Users\\VORPC\\Pictures\\Saved Pictures\\Precon Molins.jpg",
                "Correo: javecilla@precon.cemolins.es,Empresa: Precon Molins Precast Solutions,CIF: A28135361,Dirección Empresa: Ctra. Sevilla-Cádiz 552,Municipio Empresa: Dos Hermanas,Código Postal Empresa: 41704,Convenio: Derivado del Cemento,Nombre: Joaquín,Apellidos: Avecilla Barrera,DNI: 49030785Y,Dirección: Manzanilla,Localidad: Dos Hermanas,Código Postal: 41702,Teléfono: 658558346,Delegado de Prevención: Sí,Afiliado: NS/NC,Sector: Construcción y Minería,WhatsApp: https://chat.whatsapp.com/FaUlzvzaPswHX0mBdb8X7x,Imagen: C:\\Users\\VORPC\\Pictures\\Saved Pictures\\Precon Molins.jpg",
                "Correo: fuente.gomez@gmail.com,Empresa: Precon Molins Precast Solutions,CIF: A28135362,Dirección Empresa: Ctra. Sevilla-Cádiz 552,Municipio Empresa: Dos Hermanas,Código Postal Empresa: 41704,Convenio: Derivado del Cemento,Nombre: Jesús,Apellidos: De la Fuente Gomez,DNI: 28594213S,Dirección: Calle jilguero n°1,Localidad: Utrera,Código Postal: 41710,Teléfono: 662088101,Delegado de Prevención: Sí,Afiliado: Si,Sector: Construcción y Minería,WhatsApp: https://chat.whatsapp.com/FaUlzvzaPswHX0mBdb8X7x,Imagen: C:\\Users\\VORPC\\Pictures\\Saved Pictures\\Precon Molins.jpg",
                "Correo: romerogalgo75.jr@gmail.com,Empresa: Precon Molins Precast Solutions,CIF: A28135363,Dirección Empresa: Ctra. Sevilla-Cádiz 552,Municipio Empresa: Dos Hermanas,Código Postal Empresa: 41704,Convenio: Derivado del Cemento,Nombre: Francisco Javier,Apellidos: Romero García,DNI: 44958672C,Dirección: Barriada el Tinte Bloque 26BD,Localidad: Utrera,Código Postal: 41710,Teléfono: 626750711,Delegado de Prevención: No,Afiliado: No,Sector: Construcción y Minería,WhatsApp: https://chat.whatsapp.com/FaUlzvzaPswHX0mBdb8X7x,Imagen: C:\\Users\\VORPC\\Pictures\\Saved Pictures\\Precon Molins.jpg",
                "Correo: falcongutierrezantonio@gmail.com,Empresa: Precon Molins Precast Solutions,CIF: A28135364,Dirección Empresa: Ctra. Sevilla-Cádiz 552,Municipio Empresa: Dos Hermanas,Código Postal Empresa: 41704,Convenio: Derivado del Cemento,Nombre: Antonio,Apellidos: Falcon Gutiérrez,DNI: 52238159F,Dirección: Giralda n 2,Localidad: Los Palacios y Villafranca,Código Postal: 41720,Teléfono: 671685387,Delegado de Prevención: No,Afiliado: No,Sector: Construcción y Minería,WhatsApp: https://chat.whatsapp.com/FaUlzvzaPswHX0mBdb8X7x,Imagen: C:\\Users\\VORPC\\Pictures\\Saved Pictures\\Precon Molins.jpg"
        };
        String outputPath = "G:\\Mi unidad\\Contactos";

        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            for (String contact : contacts) {
                String[] details = contact.split(",");
                String email = details[0].split(": ")[1];
                String company = details[1].split(": ")[1];
                String cif = details[2].split(": ")[1];
                String companyAddress = details[3].split(": ")[1];
                String companyCity = details[4].split(": ")[1];
                String companyPostalCode = details[5].split(": ")[1];
                String convenio = details[6].split(": ")[1];
                String firstName = details[7].split(": ")[1];
                String lastName = details[8].split(": ")[1];
                String dni = details[9].split(": ")[1];
                String address = details[10].split(": ")[1];
                String city = details[11].split(": ")[1];
                String postalCode = details[12].split(": ")[1];
                String phone = details[13].split(": ")[1];
                String preventionDelegate = details[14].split(": ")[1];
                String isAffiliated = details[15].split(": ")[1];
                String sector = details[16].split(": ")[1];
                String whatsappLink = details[17].split(": ")[1];
                String imagePath = details[18].split(": ")[1];

                String vCard = "BEGIN:VCARD\n" +
                        "VERSION:3.0\n" +
                        "FN:" + firstName + " " + lastName + "\n" +
                        "N:" + lastName + ";" + firstName + ";;;\n" +
                        "EMAIL:" + email + "\n" +
                        "ORG:" + company + "\n" +
                        "TITLE:" + sector + "\n" +
                        "NOTE:CIF Empresa: " + cif + "\\nConvenio: " + convenio + "\\nDelegado de Prevención: " + preventionDelegate + "\\nAfiliado: " + isAffiliated + "\n" +
                        "ADR;TYPE=WORK:;;" + companyAddress + ";" + companyCity + ";;" + companyPostalCode + "\n" +
                        "ADR;TYPE=HOME:;;" + address + ";" + city + ";;" + postalCode + "\n" +
                        "TEL;TYPE=CELL:" + phone + "\n" +
                        "PHOTO;ENCODING=BASE64;TYPE=JPG:" + encodeImageToBase64(imagePath) + "\n" +
                        (whatsappLink.isEmpty() ? "" : "URL:" + whatsappLink + "\n") +
                        "END:VCARD\n";

                fos.write(vCard.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String encodeImageToBase64(String imagePath) {
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
            return java.util.Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
