package org.example;

import org.example.dto.Club;
import org.example.dto.Player;
import org.example.dto.Stadium;
import org.example.dto.Staff;
import org.example.util.DateParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        String baseUrl ="https://vpf.vn";
        try {
            List<Player> playerList = new ArrayList<>();
            List<Staff> staffList = new ArrayList<>();
            List<Stadium> stadiumList = new ArrayList<>();
            List<Club> clubList = new ArrayList<>();
            String clubListURL=baseUrl+"/cac-doi-bong-v-league/";
            Document document = Jsoup
                    .connect(clubListURL)
                    .get();
            Elements divClubs = document.getElementsByClass("team-block");
            for (Element divClub : divClubs){
                String clubImage = divClub.select(".team-logo").select("img").attr("src");
                String clubName = divClub.select(".team-title").text();
                String clubDetailURL=divClub.select(".team-title").attr("href");
                Document clubDocument =Jsoup
                        .connect(clubDetailURL)
                        .get();
                Element playerListDiv = clubDocument.getElementById("stab_players_stats");
                Element stadiumDiv = clubDocument.getElementById("stab_venue");
                Elements playerTaglist =playerListDiv.select("tbody > tr");
                for (Element playerDiv: playerTaglist){
                    String imageURL = playerDiv.select("img").attr("src");
                    String name = playerDiv.select("img").attr("title");
                    String dateOfBirth = playerDiv.select("td:nth-child(6)").text();
                    int heightCm =0;
                    String heightCmString =playerDiv.select("td:nth-child(4)").text();
                    if(!heightCmString.isEmpty())
                           heightCm= parseInt(heightCmString);
                    int weightKg =0;
                    String weightKgString =playerDiv.select("td:nth-child(5)").text();
                    if (!weightKgString.isEmpty()){
                        weightKg=parseInt(weightKgString);
                    }
                    String role=playerDiv.select("td:nth-child(3)").text();
                    String number=playerDiv.select("td:nth-child(2)").text();
                    if(number.isEmpty()){
                        Staff staff = Staff.builder()
                                .imageURL(imageURL)
                                .role(role)
                                .name(name)
                                .build();
                        staffList.add(staff);
                    }else {
                        Player player = Player.builder()
                                .imageURL(imageURL)
                                .role(role)
                                .name(name)
                                .heightCm(heightCm)
                                .weightKg(weightKg)
                                .dateOfBirth(DateParser.formatDate(dateOfBirth))
                                .build();
                        playerList.add(player);
                    }
                }


                String stadiumName = stadiumDiv.select(".venue-info-title").text();
                String stadiumAddress = stadiumDiv.select(".venue-info-list").select("li").first().text();
                String stadiumImageURL = stadiumDiv.select(".venue-gallery").select("a").attr("href");
                Club club =Club.builder()
                        .imageURL(clubImage)
                        .name(clubName)
                        .headQuarter(stadiumAddress.replace("Địa chỉ: ", ""))
                        .build();
                clubList.add(club);
                Stadium stadium= Stadium.builder()
                        .name(stadiumName)
                        .address(stadiumAddress.replace("Địa chỉ: ", ""))
                        .imageURL(stadiumImageURL)
                        .build();
                stadiumList.add(stadium);
            }
            System.out.println("Stadium List: " +stadiumList.toString());
            System.out.println("Club List: " +clubList.toString());
            System.out.println("Player List: " +playerList.toString());
            System.out.println("Staff List: " +staffList.toString());
            File csvPlayerFile = new File("player.csv");
            File csvClubFile = new File("club.csv");
            File csvStadiumFile = new File("stadium.csv");
            File csvStaffFile = new File("staff.csv");

            try (PrintWriter printWriter = new PrintWriter(csvPlayerFile, StandardCharsets.UTF_8)) {
                // to handle BOM
                printWriter.write('\ufeff');

                // iterating over all quotes
                for (Player player : playerList) {
                    // converting the quote data into a
                    // list of strings
                    List<String> row = new ArrayList<>();

                    // wrapping each field with between quotes
                    // to make the CSV file more consistent
                    row.add("\"" + player.getName() + "\"");
                    row.add("\"" + player.getNumber() + "\"");
                    row.add("\"" + player.getRole() + "\"");
                    row.add("\"" + player.getHeightCm() + "\"");
                    row.add("\"" + player.getWeightKg() + "\"");
                    row.add("\"" + player.getDateOfBirth() + "\"");
                    row.add("\"" + player.getImageURL() + "\"");

                    // printing a CSV line
                    printWriter.println(String.join(",", row));
                }
            }
            try (PrintWriter printWriter = new PrintWriter(csvClubFile, StandardCharsets.UTF_8)) {
                // to handle BOM
                printWriter.write('\ufeff');

                // iterating over all quotes
                for (Club club : clubList) {
                    // converting the quote data into a
                    // list of strings
                    List<String> row = new ArrayList<>();

                    // wrapping each field with between quotes
                    // to make the CSV file more consistent
                    row.add(club.getName());
                    row.add(club.getHeadQuarter());
                    row.add(club.getImageURL());
                    // printing a CSV line
                    printWriter.println(String.join(",", row));
                }
            }
            try (PrintWriter printWriter = new PrintWriter(csvStadiumFile, StandardCharsets.UTF_8)) {
                // to handle BOM
                printWriter.write('\ufeff');

                // iterating over all quotes
                for (Stadium stadium : stadiumList) {
                    // converting the quote data into a
                    // list of strings
                    List<String> row = new ArrayList<>();

                    // wrapping each field with between quotes
                    // to make the CSV file more consistent
                    row.add(stadium.getName());
                    row.add(stadium.getAddress());
                    row.add(stadium.getImageURL());
                    // printing a CSV line
                    printWriter.println(String.join(",", row));
                }
            }
            try (PrintWriter printWriter = new PrintWriter(csvStaffFile, StandardCharsets.UTF_8)) {
                // to handle BOM
                printWriter.write('\ufeff');

                // iterating over all quotes
                for (Staff staff : staffList) {
                    // converting the quote data into a
                    // list of strings
                    List<String> row = new ArrayList<>();

                    // wrapping each field with between quotes
                    // to make the CSV file more consistent
                    row.add(staff.getName());
                    row.add(staff.getRole());
                    row.add(staff.getImageURL());
                    // printing a CSV line
                    printWriter.println(String.join(",", row));
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}