package com.example.planetze.models;

import java.util.HashMap;
import java.util.Map;

public class CountryAverageScore {
    public Map<String, Double> countryavg;
    public CountryAverageScore() {
        String exceldata = "Afghanistan,0.29536375\n" +
                "Africa,0.99422127\n" +
                "Albania,1.7432004\n" +
                "Algeria,3.9272263\n" +
                "Andorra,4.6171236\n" +
                "Angola,0.45155162\n" +
                "Anguilla,8.752724\n" +
                "Antigua and Barbuda,6.4218745\n" +
                "Argentina,4.2378173\n" +
                "Armenia,2.3045583\n" +
                "Aruba,8.133404\n" +
                "Asia,4.611434\n" +
                "Asia (excl. China and India),4.017375\n" +
                "Australia,14.985412\n" +
                "Austria,6.8781943\n" +
                "Azerbaijan,3.6746833\n" +
                "Bahamas,5.1708703\n" +
                "Bahrain,25.672274\n" +
                "Bangladesh,0.5964455\n" +
                "Barbados,4.3772573\n" +
                "Belarus,6.1669006\n" +
                "Belgium,7.6875386\n" +
                "Belize,1.7894346\n" +
                "Benin,0.631487\n" +
                "Bermuda,6.9370627\n" +
                "Bhutan,1.3489918\n" +
                "Bolivia,1.7583066\n" +
                "Bonaire Sint Eustatius and Saba,4.083284\n" +
                "Bosnia and Herzegovina,6.1034565\n" +
                "Botswana,2.838951\n" +
                "Brazil,2.2454574\n" +
                "British Virgin Islands,5.0039577\n" +
                "Brunei,23.950201\n" +
                "Bulgaria,6.8044534\n" +
                "Burkina Faso,0.26295447\n" +
                "Burundi,0.06194545\n" +
                "Cambodia,1.1900775\n" +
                "Cameroon,0.34292704\n" +
                "Canada,14.249212\n" +
                "Cape Verde,0.9588915\n" +
                "Central African Republic,0.040548485\n" +
                "Chad,0.13367727\n" +
                "Chile,4.3041654\n" +
                "China,7.992761\n" +
                "Colombia,1.9223082\n" +
                "Comoros,0.49327007\n" +
                "Congo,1.2447897\n" +
                "Cook Islands,3.9950094\n" +
                "Costa Rica,1.5226681\n" +
                "Cote d'Ivoire,0.41668788\n" +
                "Croatia,4.348515\n" +
                "Cuba,1.8659163\n" +
                "Curacao,9.189007\n" +
                "Cyprus,5.616782\n" +
                "Czechia,9.3357525\n" +
                "Democratic Republic of Congo,0.036375992\n" +
                "Denmark,4.940161\n" +
                "Djibouti,0.40418932\n" +
                "Dominica,2.1058853\n" +
                "Dominican Republic,2.1051137\n" +
                "East Timor,0.49869007\n" +
                "Ecuador,2.3117273\n" +
                "Egypt,2.333106\n" +
                "El Salvador,1.2174718\n" +
                "Equatorial Guinea,3.0307202\n" +
                "Eritrea,0.18914719\n" +
                "Estonia,7.77628\n" +
                "Eswatini,1.0527312\n" +
                "Ethiopia,0.15458965\n" +
                "Europe,6.8578663\n" +
                "Europe (excl. EU-27),7.886797\n" +
                "Europe (excl. EU-28),8.817789\n" +
                "European Union (27),6.1743994\n" +
                "European Union (28),5.983708\n" +
                "Faroe Islands,14.084624\n" +
                "Fiji,1.1550449\n" +
                "Finland,6.5267396\n" +
                "France,4.603891\n" +
                "French Polynesia,2.8509297\n" +
                "Gabon,2.3882635\n" +
                "Gambia,0.2847278\n" +
                "Georgia,2.962545\n" +
                "Germany,7.9837584\n" +
                "Ghana,0.6215505\n" +
                "Greece,5.7451057\n" +
                "Greenland,10.473997\n" +
                "Grenada,2.7133646\n" +
                "Guatemala,1.0756185\n" +
                "Guinea,0.35742033\n" +
                "Guinea-Bissau,0.15518051\n" +
                "Guyana,4.3736935\n" +
                "Haiti,0.21119381\n" +
                "High-income countries,10.132565\n" +
                "Honduras,1.0696708\n" +
                "Hong Kong,4.081913\n" +
                "Hungary,4.449911\n" +
                "Iceland,9.499798\n" +
                "India,1.9966822\n" +
                "Indonesia,2.6456614\n" +
                "Iran,7.7993317\n" +
                "Iraq,4.024638\n" +
                "Ireland,7.7211185\n" +
                "Israel,6.208912\n" +
                "Italy,5.726825\n" +
                "Jamaica,2.2945588\n" +
                "Japan,8.501681\n" +
                "Jordan,2.0301995\n" +
                "Kazakhstan,13.979704\n" +
                "Kenya,0.45998666\n" +
                "Kiribati,0.5184742\n" +
                "Kosovo,4.830646\n" +
                "Kuwait,25.578102\n" +
                "Kyrgyzstan,1.4251612\n" +
                "Laos,3.0803475\n" +
                "Latvia,3.561689\n" +
                "Lebanon,4.3543963\n" +
                "Lesotho,1.3594668\n" +
                "Liberia,0.1653753\n" +
                "Libya,9.242238\n" +
                "Liechtenstein,3.8097827\n" +
                "Lithuania,4.606163\n" +
                "Low-income countries,0.28005043\n" +
                "Lower-middle-income countries,1.777996\n" +
                "Luxembourg,11.618432\n" +
                "Macao,1.5127679\n" +
                "Madagascar,0.14871116\n" +
                "Malawi,0.10262384\n" +
                "Malaysia,8.576508\n" +
                "Maldives,3.2475724\n" +
                "Mali,0.31153768\n" +
                "Malta,3.1035979\n" +
                "Marshall Islands,3.6353714\n" +
                "Mauritania,0.957337\n" +
                "Mauritius,3.2697906\n" +
                "Mexico,4.0153365\n" +
                "Micronesia (country),1.3243006\n" +
                "Moldova,1.6565942\n" +
                "Mongolia,11.150772\n" +
                "Montenegro,3.6558185\n" +
                "Montserrat,4.8447766\n" +
                "Morocco,1.8263615\n" +
                "Mozambique,0.24274588\n" +
                "Myanmar,0.6445672\n" +
                "Namibia,1.5399038\n" +
                "Nauru,4.1700416\n" +
                "Nepal,0.5074035\n" +
                "Netherlands,7.1372175\n" +
                "New Caledonia,17.641167\n" +
                "New Zealand,6.212154\n" +
                "Nicaragua,0.79879653\n" +
                "Niger,0.116688\n" +
                "Nigeria,0.5891771\n" +
                "Niue,3.8729508\n" +
                "North America,10.5346775\n" +
                "North America (excl. USA),4.741475\n" +
                "North Korea,1.9513915\n" +
                "North Macedonia,3.6245701\n" +
                "Norway,7.5093055\n" +
                "Oceania,9.85179\n" +
                "Oman,15.730261\n" +
                "Pakistan,0.84893465\n" +
                "Palau,12.123921\n" +
                "Palestine,0.6660658\n" +
                "Panama,2.699258\n" +
                "Papua New Guinea,0.77131313\n" +
                "Paraguay,1.3299496\n" +
                "Peru,1.7891879\n" +
                "Philippines,1.3014648\n" +
                "Poland,8.106886\n" +
                "Portugal,4.050785\n" +
                "Qatar,37.601273\n" +
                "Romania,3.739777\n" +
                "Russia,11.416899\n" +
                "Rwanda,0.112346195\n" +
                "Saint Helena,3.2986484\n" +
                "Saint Kitts and Nevis,4.708081\n" +
                "Saint Lucia,2.6149206\n" +
                "Saint Pierre and Miquelon,10.293288\n" +
                "Saint Vincent and the Grenadines,2.2964725\n" +
                "Samoa,1.1218625\n" +
                "Sao Tome and Principe,0.5816142\n" +
                "Saudi Arabia,18.197495\n" +
                "Senegal,0.6738352\n" +
                "Serbia,6.024712\n" +
                "Seychelles,6.1495123\n" +
                "Sierra Leone,0.13124847\n" +
                "Singapore,8.911513\n" +
                "Sint Maarten (Dutch part),14.352394\n" +
                "Slovakia,6.051555\n" +
                "Slovenia,5.9979916\n" +
                "Solomon Islands,0.41232163\n" +
                "Somalia,0.03676208\n" +
                "South Africa,6.7461643\n" +
                "South America,2.4865332\n" +
                "South Korea,11.598764\n" +
                "South Sudan,0.1680176\n" +
                "Spain,5.1644425\n" +
                "Sri Lanka,0.7936504\n" +
                "Sudan,0.4696261\n" +
                "Suriname,5.8029985\n" +
                "Sweden,3.6069093\n" +
                "Switzerland,4.0478554\n" +
                "Syria,1.2490375\n" +
                "Taiwan,11.630868\n" +
                "Tajikistan,1.0064901\n" +
                "Tanzania,0.23771806\n" +
                "Thailand,3.7762568\n" +
                "Togo,0.2910665\n" +
                "Tonga,1.7686282\n" +
                "Trinidad and Tobago,22.423758\n" +
                "Tunisia,2.879285\n" +
                "Turkey,5.1052055\n" +
                "Turkmenistan,11.03418\n" +
                "Turks and Caicos Islands,7.636793\n" +
                "Tuvalu,1.0004411\n" +
                "Uganda,0.12744623\n" +
                "Ukraine,3.5578535\n" +
                "United Arab Emirates,25.833244\n" +
                "United Kingdom,4.7201805\n" +
                "United States,14.949616\n" +
                "Upper-middle-income countries,6.2268133\n" +
                "Uruguay,2.3060381\n" +
                "Uzbekistan,3.4830604\n" +
                "Vanuatu,0.6363055\n" +
                "Venezuela,2.7168686\n" +
                "Vietnam,3.4995174\n" +
                "Wallis and Futuna,2.2819076\n" +
                "World,4.658219\n" +
                "Yemen,0.33701748\n" +
                "Zambia,0.44570068\n" +
                "Zimbabwe,0.542628";

        countryavg = new HashMap<>();

        for (String line :exceldata.split("\n")) {
            String[] row = line.split(",");
            countryavg.put(row[0], Double.parseDouble(row[1]));
        }
    }
}
