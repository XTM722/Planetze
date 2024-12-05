package com.example.planetze.models;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class ScoreCalculate {
    //Set up field
    static double score;
    static double tran_score;
    static double food_score;
    static double heat_home_score;
    static double heat_water_score;
    static double heat_score;
    static double house_score;
    static double clothe_score;
    static double consume_score;
    static Map<String, String> answers;
    private static DatabaseReference mDatabase;



    //helper function
    public static double CalculateBillScore(double a, double b, double c, double d, double e) {
        if ("Under $50".equals(answers.get("q15"))) {
            score = score + a;
        }
        if ("$50-$100".equals(answers.get("q15"))) {
            score = score + b;
        }
        if ("$100-$150".equals(answers.get("q15"))) {
            score = score + c;
        }
        if ("$150-$200".equals(answers.get("q15"))) {
            score = score + d;
        }
        if ("Over $200".equals(answers.get("q15"))) {
            score = score + e;
        }
        return score;
    }

    public static boolean SameAnswer(){
        return answers.get("q16").equals(answers.get("q17"));
    }

    public static double Device_Frequency_Buyer(String frequency, double a, double b, double c){
        if (frequency.equals("Occasionally")){
            score = score - a;
        }
        if (frequency.equals("Frequently")){
            score = score - b;
        }
        if (frequency.equals("Always")){
            score = score - c;
        }
        return score;
    }
    public static void calculate(User user){
        score = 0.0;
        tran_score = 0.0;
        food_score = 0.0;
        heat_home_score = 0.0;
        heat_water_score = 0.0;
        heat_score = 0.0;
        house_score = 0.0;
        clothe_score = 0.0;
        consume_score = 0.0;
        answers= user.answers;

        //for loop to make sure that no null point
        //Part 1 Transportation
        //solve q1 with q2 q3
        if ("Yes".equals(answers.get("q1"))) {

            //q2 with gasoline with q3 distance
            if ("Gasoline".equals(answers.get("q2"))) {
                if ("Up to 5,000 km (3,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.24 * 5;
                }
                if ("5,000–10,000 km (3,000–6,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.24 * 10;
                }
                if ("10,000–15,000 km (6,000–9,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.24 * 15;
                }
                if ("15,000–20,000 km (9,000–12,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.24 * 20;
                }
                if ("20,000–25,000 km (12,000–15,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.24 * 25;
                }
                if ("More than 25,000 km (15,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.24 * 35;
                }
            }

            //q2 with Diesel with q3 distance
            if ("Diesel".equals(answers.get("q2"))) {
                if ("Up to 5,000 km (3,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.27 * 5;
                }
                if ("5,000–10,000 km (3,000–6,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.27 * 10;
                }
                if ("10,000–15,000 km (6,000–9,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.27 * 15;
                }
                if ("15,000–20,000 km (9,000–12,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.27 * 20;
                }
                if ("20,000–25,000 km (12,000–15,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.27 * 25;
                }
                if ("More than 25,000 km (15,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.27 * 35;
                }
            }

            //q2 with Hybrid with q3 distance
            if ("Hybrid".equals(answers.get("q2"))) {
                if ("Up to 5,000 km (3,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.16 * 5;
                }
                if ("5,000–10,000 km (3,000–6,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.16 * 10;
                }
                if ("10,000–15,000 km (6,000–9,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.16 * 15;
                }
                if ("15,000–20,000 km (9,000–12,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.16 * 20;
                }
                if ("20,000–25,000 km (12,000–15,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.16 * 25;
                }
                if ("More than 25,000 km (15,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.16 * 35;
                }
            }
            //q2 with Electric with q3 distance
            if ("Electric".equals(answers.get("q2"))) {
                if ("Up to 5,000 km (3,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.05 * 5;
                }
                if ("5,000–10,000 km (3,000–6,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.05 * 10;
                }
                if ("10,000–15,000 km (6,000–9,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.05 * 15;
                }
                if ("15,000–20,000 km (9,000–12,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.05 * 20;
                }
                if ("20,000–25,000 km (12,000–15,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.05 * 25;
                }
                if ("More than 25,000 km (15,000 miles)".equals(answers.get("q3"))) {
                    score = score + 0.05 * 35;
                }
            }
            //Public Transportation q4 & q5

            //Occasionally with 5 frequency
            if ("Occasionally (1-2 times/week)".equals(answers.get("q4"))) {
                if ("Under 1 hour".equals(answers.get("q5"))) {
                    score = score + 0.246;
                }
                if ("1-3 hours".equals(answers.get("q5"))) {
                    score = score + 0.819;
                }
                if ("3-5 hours".equals(answers.get("q5"))) {
                    score = score + 1.638;
                }
                if ("5-10 hours".equals(answers.get("q5"))) {
                    score = score + 3.071;
                }
                if ("More than 10 hours".equals(answers.get("q5"))) {
                    score = score + 4.095;
                }
            }

            //Frequently with 5 frequency
            if ("Frequently (3-4 times/week)".equals(answers.get("q4"))) {
                if ("Under 1 hour".equals(answers.get("q5"))) {
                    score = score + 0.573;
                }
                if ("1-3 hours".equals(answers.get("q5"))) {
                    score = score + 1.911;
                }
                if ("3-5 hours".equals(answers.get("q5"))) {
                    score = score + 3.822;
                }
                if ("5-10 hours".equals(answers.get("q5"))) {
                    score = score + 7.166;
                }
                if ("More than 10 hours".equals(answers.get("q5"))) {
                    score = score + 9.555;
                }
            }

            //Always with 5 frequency
            if ("Always (5+ times/week)".equals(answers.get("q4"))) {
                if ("Under 1 hour".equals(answers.get("q5"))) {
                    score = score + 0.573;
                }
                if ("1-3 hours".equals(answers.get("q5"))) {
                    score = score + 1.911;
                }
                if ("3-5 hours".equals(answers.get("q5"))) {
                    score = score + 3.822;
                }
                if ("5-10 hours".equals(answers.get("q5"))) {
                    score = score + 7.166;
                }
                if ("More than 10 hours".equals(answers.get("q5"))) {
                    score = score + 9.555;
                }
            }

            //Air Travel q6
            if ("1-2 flights".equals(answers.get("q6"))) {
                score = score + 0.225;
            }
            if ("3-5 flights".equals(answers.get("q6"))) {
                score = score + 0.6;
            }
            if ("6-10 flights".equals(answers.get("q6"))) {
                score = score + 1.2;
            }
            if ("More than 10 flights".equals(answers.get("q6"))) {
                score = score + 1.8;
            }

            //Air Travel q7
            if ("1-2 flights".equals(answers.get("q7"))) {
                score = score + 0.825;
            }
            if ("3-5 flights".equals(answers.get("q7"))) {
                score = score + 2.2;
            }
            if ("6-10 flights".equals(answers.get("q7"))) {
                score = score + 4.4;
            }
            if ("More than 10 flights".equals(answers.get("q7"))) {
                score = score + 6.6;
            }

        }//q1
        tran_score = score;

        //Part 2 Food
        //q8
        //Meat
        if ("Meat-based (eat all types of animal products)".equals(answers.get("q8"))) {

            //beef
            if ("Daily".equals(answers.get("q9_a"))) {
                score = score + 2.5;
            }
            if ("Frequently (3-5 times/week)".equals(answers.get("q9_a"))) {
                score = score + 1.9;
            }
            if ("Occasionally (1-2 times/week)".equals(answers.get("q9_a"))) {
                score = score + 1.3;
            }

            //pork
            if ("Daily".equals(answers.get("q9_b"))) {
                score = score + 1.45;
            }
            if ("Frequently (3-5 times/week)".equals(answers.get("q9_b"))) {
                score = score + 0.86;
            }
            if ("Occasionally (1-2 times/week)".equals(answers.get("q9_b"))) {
                score = score + 0.45;
            }

            //chicken
            if ("Daily".equals(answers.get("q9_c"))) {
                score = score + 0.95;
            }
            if ("Frequently (3-5 times/week)".equals(answers.get("q9_c"))) {
                score = score + 0.6;
            }
            if ("Occasionally (1-2 times/week)".equals(answers.get("q9_c"))) {
                score = score + 0.2;
            }

            //fish
            if ("Daily".equals(answers.get("q9_d"))) {
                score = score + 0.8;
            }
            if ("Frequently (3-5 times/week)".equals(answers.get("q9_d"))) {
                score = score + 0.5;
            }
            if ("Occasionally (1-2 times/week)".equals(answers.get("q9_d"))) {
                score = score + 0.15;
            }
        }

        //q8 with vegetable stream
        //Vegetarian
        if ("Vegetarian".equals(answers.get("q8"))) {
            score = score + 1;
        }
        //Vegan
        if ("Vegan".equals(answers.get("q8"))) {
            score = score + 0.5;
        }
        //Pescatarian (fish/seafood)
        if ("Pescatarian (fish/seafood)".equals(answers.get("q8"))) {
            score = score + 1.5;
        }

        //q10
        if ("Rarely".equals(answers.get("q10"))) {
            score = score + 0.0234;
        }
        if ("Occasionally".equals(answers.get("q10"))) {
            score = score + 0.0702;
        }
        if ("Frequently".equals(answers.get("q10"))) {
            score = score + 0.1404;
        }
        food_score = score - tran_score;

        //House Part
        //Type of house
        // detached house
        if ("Detached house".equals(answers.get("q11"))) {
            //1 Occupant
            if ("1".equals(answers.get("q12"))) {
                //1 Occupant Natural Gas Under 1000 sq. ft. 5 different bills
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.4, 2.44, 2.61, 2.78, 3.1);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.2, 0.4, 1.2, 1.7, 2.3);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.1, 5.2, 6.1, 7.2, 8.2);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.87, 4.4, 5.4, 6.4, 7.4);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.17, 2.34, 2.51, 2.68, 3);
                    }

                }

                // 1000-2000 sq. ft.
                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.8, 5.9, 6.5, 7.1, 8.3);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.3, 0.6, 1.2, 1.8, 2.4);

                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.8, 5.3, 6.2, 7.2, 8.2);

                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.77, 3.94, 7.1, 4.28, 4.6);

                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.67, 3.84, 4.01, 4.18, 4.5);

                    }
                }
                // over 2000 sq
                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.8, 3, 3.2, 3.4, 3.6);

                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.32, 0.6, 1.8, 2.7, 3.6);

                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.4, 10.5, 14, 17.5, 21);

                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.57, 5.74, 5.8, 5.852, 6.1);

                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.17, 4.34, 4.51, 4.68, 5);

                    }

                }
            }
            // 2 Occupants
            if ("2".equals(answers.get("q12"))) {
                //Natural Gas Under 1000 sq. ft. 5 different bills
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.6, 2.64, 2.81, 2.98, 3.1);

                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.25, 0.5, 1.45, 1.9, 2.5);

                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.65, 5.4, 6.25, 7.4, 8.4);

                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.47, 4.6, 5.6, 6.6, 7.6);

                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.37, 2.54, 2.71, 2.88, 3.2);

                    }
                }
                // 1000-2000 sq. ft.
                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3, 6.1, 6.7, 7.3, 8.5);

                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.38, 0.8, 1.45, 2, 2.6);

                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4, 5.44, 6.4, 7.4, 8.4);

                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.47, 4.46, 7.23, 4.98, 5.3);

                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.17, 4.34, 4.51, 4.68, 5);

                    }
                }
                // over 2000 sq
                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.88, 3.2, 3.4, 3.6, 3.8);

                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.45, 0.9, 2.1, 3.1, 3.8);

                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(6.2, 11, 15.5, 18.1, 22);

                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(6.17, 6.34, 6.41, 6.56, 6.84);

                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.67, 4.84, 5.01, 5.18, 5.5);

                    }
                }
            }

            // 3-4 Occupants
            if ("3-4".equals(answers.get("q12"))) {
                //Under 1000
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.7, 2.94, 3.11, 3.28, 3.6);

                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.38, 0.55, 1.6, 2.05, 2.7);

                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.2, 5.7, 6.7, 7.7, 8.7);

                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.37, 4.9, 5.9, 6.9, 7.9);

                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.67, 2.84, 3.01, 3.18, 3.5);

                    }
                }
                // 1000-2000 sq. ft.
                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.38, 6.4, 7, 7.6, 8.8);

                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.5, 1.05, 1.6, 2.25, 2.8);

                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.7, 5.8, 6.7, 7.7, 8.7);

                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.67, 5.74, 7.4, 5.985, 6.35);

                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.87, 5.04, 5.21, 5.38, 5.75);

                    }
                }
                //  Over 2000 sq. ft.
                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3, 3.5, 3.7, 4.1, 4.1);

                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.52, 1.5, 2.3, 3.4, 4);

                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(7, 12.5, 16.25, 20, 23.5);

                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(6.97, 7.24, 7.3, 7.6, 7.89);

                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.27, 5.64, 5.71, 5.98, 6.25);

                    }
                }

            }
            // 5 or more
            if ("5 or more".equals(answers.get("q12"))) {
                // Under 1000 square feet
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3, 3.24, 3.41, 3.58, 3.9);

                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.45, 0.6, 1.8, 2.2, 3);

                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.7, 6, 6.95, 8, 9);

                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.27, 5.2, 6.2, 7.2, 8.2);

                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.97, 3.14, 3.31, 3.48, 3.8);

                    }
                }
                // 1000-2000 sq. ft.
                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.86, 6.7, 7.3, 7.9, 9.1);

                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.6, 1.2, 1.8, 2.4, 3.1);

                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.35, 6.1, 7, 8, 9);

                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(6.57, 6.74, 7.55, 7.08, 7.4);

                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.67, 5.84, 6.01, 6.18, 6.5);

                    }
                }
                // Over 2000 sq. ft.
                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.23, 3.8, 4, 4.4, 4.4);

                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.675, 1.8, 2.7, 3.6, 4.2);

                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(8.9, 14, 17.5, 21, 25);

                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(7.97, 8.14, 8.23, 8.3, 8.71);

                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(6.17, 6.34, 6.51, 6.68, 7);

                    }
                }
            }// end of natural gas of detached house with 5 different occupants
        } //End of the detached house all possible result


        // Semi-detached house
        if ("Semi-detached house".equals(answers.get("q11"))) {
            //1 Occupant
            if ("1".equals(answers.get("q12"))) {

                //1 Occupant Natural Gas Under 1000 sq. ft. 5 different bills
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.16, 2.4, 2.6, 2.8, 3);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.3, 0.41, 1.21, 1.7, 2.2);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.5, 2.8, 3, 3.2, 3.4);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.2, 2.6, 2.8, 3, 3.2);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.1, 3, 3.2, 3.4, 3.6);

                    }
                }

                // 1000-2000 sq. ft.
                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.443, 4, 4.5, 5, 6);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.3, 0.6, 1.2, 1.8, 2.4);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.4, 4, 6.1, 8, 10.55);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4, 5, 7, 9, 12);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.5, 2.5, 4.1, 5.5, 7.22);
                    }
                }
                // over 2000 sq
                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.821, 7.5, 10, 12.5, 15);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.3, 1.2, 1.8, 2.4, 3);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.82, 5.5, 8.5, 11, 14);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.37, 4.54, 4.71, 4.88, 5.2);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.97, 4.14, 4.31, 4.48, 4.8);
                    }

                }
            }
            // 2 Occupants
            if ("2".equals(answers.get("q12"))) {
                //Natural Gas Under 1000 sq. ft. 5 different bills
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.349, 2.6, 2.8, 3, 3.2);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.41, 0.5, 1.45, 1.9, 2.5);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.592, 3, 3.2, 3.4, 3.6);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.3, 2.8, 3, 3.2, 3.4);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.45, 3.2, 3.4, 3.6, 3.8);
                    }
                }
                // 1000-2000 sq. ft.
                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.727, 5.2, 6, 6.5, 7.8);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.41, 0.8, 1.55, 2, 2.5);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.499, 4.6, 6.9, 8.8, 10.9);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.3, 6.2, 8, 10.2, 13.2);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.8, 2.7, 4.3, 6, 8);
                    }
                }
                // over 2000 sq
                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.01, 8.8, 12, 14.2, 16.8);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.56, 1.4, 2, 2.6, 3.5);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4, 6, 9.2, 12, 14.8);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.87, 5.04, 5.21, 5.38, 5.7);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.47, 4.64, 4.81, 4.98, 5.3);
                    }
                }
            }

            // 3-4 Occupants
            if ("3-4".equals(answers.get("q12"))) {
                //Under 1000
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.732, 2.9, 3.1, 3.3, 3.5);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.5, 0.56, 1.62, 2, 2.6);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.68, 3.3, 3.5, 3.7, 3.9);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.45, 3.1, 3.3, 3.5, 3.7);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.7, 3.5, 3.7, 3.9, 4.1);
                    }
                }
                // 1000-2000 sq. ft.
                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.151, 6.8, 7.8, 8.8, 9.8);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.55, 1.05, 1.7, 2.25, 2.8);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.599, 5.1, 7.3, 9.2, 11.2);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.7, 7.3, 9.1, 11, 14.1);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.1, 3.5, 4.85, 6.8, 8.6);
                    }
                }
                //  Over 2000 sq. ft.
                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.261, 10.8, 14, 16, 18.2);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.89, 1.65, 2.3, 2.82, 4);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.307, 7.2, 10.2, 13.5, 15.5);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.67, 5.84, 6.01, 6.18, 6.5);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.27, 5.34, 5.61, 5.78, 6.15);
                    }
                }

            }
            // 5 or more
            if ("5 or more".equals(answers.get("q12"))) {
                // Under 1000 square feet
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.199, 3.2, 3.4, 3.6, 3.8);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.58, 0.6, 1.82, 2.2, 3);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.75, 3.6, 3.8, 4, 4.2);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.6, 3.4, 3.6, 3.8, 4);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3, 3.8, 4, 4.2, 4.4);
                    }
                }
                // 1000-2000 sq. ft.
                //5 Occupants
                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.578, 7.5, 8.5, 10, 11);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.605, 1.2, 1.8, 2.4, 3.2);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.7, 6, 8.5, 10.5, 12);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.9, 8, 10, 12, 15);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.5, 4, 5.5, 7.1, 9.1);
                    }
                }
                // Over 2000 sq. ft.
                //5 Occupants
                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.578, 12.5, 15, 17.5, 19);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1, 1.8, 2.4, 3, 4.5);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.4, 8.5, 11, 14, 16);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(6.37, 6.54, 6.71, 6.88, 7.2);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.97, 6.14, 6.31, 6.48, 6.8);
                    }
                }
            }
        } //End of the Semi-detached house all possible result
        //Townhouse && Other 因为 other 按 townhouse算
        if ("Townhouse".equals(answers.get("q11")) || "Other".equals(answers.get("q11"))) {
            //1 Occupant
            if ("1".equals(answers.get("q12"))) {
                //1 Occupant Natural Gas Under 1000 sq. ft. 5 different bills
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.971, 2.8, 3, 4, 8);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.3, 0.5, 1, 1.6, 2);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.4, 2.8, 3.6, 4.5, 5);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.5, 2.5, 3, 3.7, 5.8);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2, 2.8, 3, 3.33, 3.5);

                    }
                }
                // 1000-2000 sq. ft.
                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.443, 4, 4.3, 4.8, 9.5);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.3, 0.55, 1.2, 1.7, 2.5);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.59, 3.8, 5, 5.35, 5.37);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.17, 5.6, 6, 3.68, 6);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.4, 2.4, 4, 3.8, 4);

                    }
                }
                // over 2000 sq
                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.822, 3.6, 5, 8, 9.5);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.3, 1.2, 1.8, 2.4, 2.8);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.81, 4.3, 5.3, 5.44, 5.67);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.34, 5.9, 3.51, 3.8, 6.2);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.8, 3.5, 4.1, 4.2, 4.3);
                    }
                }
            }
            // 2 Occupants
            if ("2".equals(answers.get("q12"))) {
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas

                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.16, 2.91, 3.21, 5.5, 9.5);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.41, 0.55, 1.25, 1.75, 2.1);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.523, 3.1, 3.75, 4.6, 5.2);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.85, 2.8, 3.5, 4.5, 6.8);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.25, 3, 3.3, 3.5, 3.7);

                    }
                }
                // 1000-2000 sq. ft.

                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.75, 5, 5.5, 6.3, 10.1);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.38, 0.7, 1.35, 1.9, 2.78);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.62, 4.32, 5.8, 5.5, 5.5);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.77, 5.94, 6.2, 4.28, 6.6);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.56, 2.6, 4.31, 3.8, 4.64);

                    }
                }
                // over 2000 sq

                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.01, 3.84, 6.2, 8.3, 1.01);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.56, 1.38, 2, 2.5, 3);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3, 4.9, 5.69, 5.6, 5.8);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.94, 6.33, 4.11, 4.5, 6.9);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.07, 3.93, 4.5, 4.64, 4.7);
                    }
                }
            }

            // 3-4 Occupants
            if ("3-4".equals(answers.get("q12"))) {
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas

                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.5, 3, 3.5, 6.2, 10.2);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.5, 0.58, 1.32, 1.9, 2.3);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.6, 3.25, 3.9, 4.8, 5.3);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.1, 3.4, 4.1, 5.2, 7.2);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.5, 3.3, 3.52, 3.72, 4);

                    }
                }
                // 1000-2000 sq. ft.

                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.111, 6.5, 6.8, 8.5, 11.2);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.5, 0.95, 1.52, 2.15, 3);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.73, 4.8, 6.2, 5.72, 5.8);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.67, 6.14, 6.42, 5.18, 6.8);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.9, 3.3, 4.6, 4.22, 5);

                    }
                }
                // over 2000 sq

                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.3, 3.9, 7, 9, 10.3);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.89, 1.6, 2.2, 2.65, 3.8);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.468, 5.32, 6.25, 5.8, 6.1);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(4.84, 6.44, 5.01, 5.38, 7.5);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5, 4.36, 4.78, 5, 5.1);
                    }
                }
            }
            // 5 or more
            if ("5 or more".equals(answers.get("q12"))) {
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas

                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.8, 3.2, 3.8, 8, 12);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.55, 0.6, 1.42, 2, 3);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.72, 3.5, 4.05, 5.1, 5.6);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.5, 3.8, 5, 5.8, 7.8);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.6, 3.4, 3.8, 4, 4.3);

                    }
                }
                // 1000-2000 sq. ft.
                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.58, 7.3, 8.34, 9, 14);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.59, 1.1, 1.7, 2.4, 3.5);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.8, 5.5, 6.1, 5.9, 6.2);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.57, 6.34, 6.5, 6.08, 7.4);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.2, 3.8, 5.1, 4.4, 5.43);

                    }
                }
                // over 2000 sq

                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.6, 5.1, 8, 9.5, 11);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1, 1.75, 2.3, 2.8, 4.3);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.76, 5.8, 6.5, 6, 6.35);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.74, 6.9, 5.91, 6.2, 7.85);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(5.6, 5, 5.36, 5.4, 5.5);
                    }
                }
            }
        }//End of TownHouse part


        //Condo/Apartment
        if ("Condo/Apartment".equals(answers.get("q11"))) {
            //1 Occupant

            if ("1".equals(answers.get("q12"))) {
                //1 Occupant Natural Gas Under 1000 sq. ft. 5 different bills
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas

                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.68, 2.24, 2.8, 3.7, 5);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.2, 0.5, 0.9, 1.4, 1.9);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.32, 2.1, 3, 3.3, 5.7);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.6, 1.8, 2.8, 3, 3.5);

                    }
                }
                // 1000-2000 sq. ft.

                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.06, 2.5, 3.1, 4, 5.35);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.3, 0.55, 1.25, 1.9, 2.3);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.5, 3, 4.1, 4.55, 6);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.8, 2.2, 3.2, 3.1, 3.9);

                    }
                }
                // over 2000 sq

                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.44, 2.7, 3.67, 4.25, 6);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.35, 0.61, 1.5, 2.15, 2.6);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.8, 3.65, 4.5, 5, 6.5);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.3, 2.6, 3.5, 3.53, 4.1);
                    }
                }
            }
            // 2 Occupants
            if ("2".equals(answers.get("q12"))) {
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.87, 2.43, 3, 4.1, 7.2);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.25, 0.55, 1.1, 1.6, 2.1);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.55, 2.4, 3.3, 3.7, 6);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.85, 2, 3, 3.1, 3.6);

                    }
                }
                // 1000-2000 sq. ft.
                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.26, 2.88, 3.3, 4.7, 7.55);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.4, 0.67, 1.45, 2.3, 2.5);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.7, 3.4, 4.6, 4.95, 6.3);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.2, 2.5, 3.5, 3.3, 4.2);

                    }
                }
                // over 2000 sq
                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.727, 3.1, 3.87, 5.05, 7.8);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.57, 0.69, 1.7, 2.55, 3.1);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.1, 4.05, 5, 5.3, 6.8);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.6, 2.9, 3.7, 3.73, 4.4);
                    }
                }
            }

            // 3-4 Occupants
            if ("3-4".equals(answers.get("q12"))) {
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas

                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.17, 2.719, 3.2, 4.6, 8);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.38, 0.58, 1.2, 1.7, 2.2);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(1.8, 2.8, 3.7, 4.4, 6.6);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2, 2.3, 3.3, 3.5, 3.9);

                    }
                }
                // 1000-2000 sq. ft.

                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.54, 3.11, 3.5, 5.2, 8.2);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.5, 0.78, 1.75, 2.7, 2.7);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.1, 3.8, 5, 5.35, 7);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.5, 2.9, 3.6, 3.7, 4.5);

                    }
                }
                // over 2000 sq

                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.01, 3.3, 4.1, 5.4, 8.5);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.9, 0.82, 2, 2.85, 3.6);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.45, 4.65, 5.4, 5.6, 7.4);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.9, 3.3, 4.2, 4.2, 4.9);
                    }
                }
            }
            // 5 or more
            if ("5 or more".equals(answers.get("q12"))) {
                if ("Under 1000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.44, 2.997, 3.5, 5, 10);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.45, 0.6, 1.3, 1.9, 2.6);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2, 3.2, 4.3, 5.2, 7);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.1, 2.4, 3.5, 3.9, 4.2);

                    }
                }
                // 1000-2000 sq. ft.

                if ("1000-2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.01, 3.32, 3.9, 5.9, 10.5);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.62, 0.9, 2.1, 3, 3.1);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.3, 4.2, 5.4, 5.65, 7.4);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.7, 3.3, 4, 4.1, 4.7);

                    }
                }
                // over 2000 sq

                if ("Over 2000 sq. ft.".equals(answers.get("q13"))) {
                    //Natural Gas
                    if ("Natural Gas".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.577, 3.6, 4.3, 6.2, 11.1);
                    }
                    //Electricity
                    if ("Electricity".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0.98, 0.98, 2.35, 3.15, 4);
                    }
                    // Oil
                    if ("Oil".equals(answers.get("q14"))) {
                        score = CalculateBillScore(0, 0, 0, 0, 0);
                    }
                    //  Propane
                    if ("Propane".equals(answers.get("q14"))) {
                        score = CalculateBillScore(2.6, 5.15, 5.7, 6, 7.8);
                    }
                    // Wood
                    if ("Wood".equals(answers.get("q14"))) {
                        score = CalculateBillScore(3.3, 3.6, 4.6, 4.63, 5.1);
                    }
                }
            }
        }//End of the Cando/Apartment
        heat_home_score = score - tran_score - food_score;
        //Q16
        if(SameAnswer()){
            score = score + heat_home_score;
        }

        if (!SameAnswer()){
            score = score + 0.233;
        }

        heat_water_score = score - tran_score - food_score - heat_home_score;

        heat_score = heat_water_score + heat_home_score;

        //Q17
        if ("Yes, primarily (more than 50% of energy use)".equals(answers.get("q17"))){
            score = score - 6;
        }
        if ("Yes, primarily (more than 50% of energy use)".equals(answers.get("q17"))){
            score = score - 4;
        }

        house_score = score - tran_score - food_score;


        //Consumption
        //Q18
        if ("Monthly".equals(answers.get("q18"))){
            score = score + 0.36;
        }
        if ("Quarterly".equals(answers.get("q18"))){
            score = score + 0.12;
        }
        if ("Annually".equals(answers.get("q18"))){
            score = score + 0.1;
        }
        if ("Rarely".equals(answers.get("q18"))){
            score = score + 5;
        }
        clothe_score = score - tran_score - food_score - house_score;

        //Q19
        if ("Yes, regularly".equals(answers.get("q19"))){
            score = score - 0.5 * clothe_score;
        }
        if ("Yes, occasionally".equals(answers.get("q19"))){
            score = score - 0.3 * clothe_score;
        }

        //Q20
        if ("1".equals(answers.get("q20"))){
            score = score + 0.3;
        }
        if ("2".equals(answers.get("q20"))){
            score = score + 0.6;
        }

        if ("3".equals(answers.get("q20"))){
            score = score + 0.9;
        }
        if ("4 or more times".equals(answers.get("q20"))){
            score = score + 1.2;
        }

        //Q21
        if ("Monthly".equals(answers.get("q18"))){
            score = Device_Frequency_Buyer(answers.get("q21"),0.054,0.108,0.18);
        }
        if ("Annually".equals(answers.get("q18"))){
            score = Device_Frequency_Buyer(answers.get("q21"),0.015,0.03,0.05);
        }
        if ("Rarely".equals(answers.get("q18"))){
            score = Device_Frequency_Buyer(answers.get("q21"),0.00075,0.0015,0.0025);
        }

        //Q20 more
        if ("1".equals(answers.get("q18"))){
            score = Device_Frequency_Buyer(answers.get("q20"),0.045,0.06,0.09);
        }
        if ("2".equals(answers.get("q18"))){
            score = Device_Frequency_Buyer(answers.get("q20"),0.06,0.12,0.18);
        }

        if ("3".equals(answers.get("q18"))){
            score = Device_Frequency_Buyer(answers.get("q20"),0.09,0.18,0.27);
        }
        if ("4 0r more".equals(answers.get("q18"))){
            score = Device_Frequency_Buyer(answers.get("q20"),0.12,0.24,0.36);
        }

        user.score = Math.max(0.0,score);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference userRef = mDatabase.child("Users").child(user.userID);
        userRef.child("score").setValue(score)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                }
                else {
                }
            });



    }

}
