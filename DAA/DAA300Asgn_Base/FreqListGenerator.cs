using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    /// This class is responsible for generating a frequency list of the 
    /// characters provided in the input text. It also has functions to
    /// retreive the list for use for outside classes, as well as a method
    /// to determine if any of the characters are invalid.
    public class FreqListGenerator
    {
        private List<Node> freqList;

        /// Default constructor
        public FreqListGenerator()
        {
            freqList = new List<Node>();
        }

        /// Generates a frequency table based on a provided char array.
        /// Returns a string with this information in table form. To 
        /// retreive the list in node form, call GetFreqList() after.
        public String CreateFreqTable(char[] charArray)
        {
            int[] frequencyArray = new int[64];
            foreach (char c in charArray)
            {
                int number = 0;
                if ((c >= '0') && (c <= '9'))
                    number = (int)c + 5;
                else if ((c >= 'a') && (c <= 'z'))
                    number = (int)c - 70;
                else if ((c >= 'A') && (c <= 'Z'))
                    number = (int)c - 64;
                else if (c == '\n')
                {
                    number = 63;
                    frequencyArray[0]--;
                }
                else if (c == '\r')
                {
                    /// Carriage return, already handled above.
                }
                else if (c == ' ')
                    number = 0;
                else
                {
                    /// Returns a string of invalid characters
                    String errorMsg = "";
                    foreach (char d in charArray)
                    {
                        if (!CharIsValid(d))
                            errorMsg += d;
                    }
                    return errorMsg;
                }
                    /// Illegal character detected. Returns to handle error.

                frequencyArray[number]++;
            }
            return (PrintFreq(frequencyArray));
        }

        /// Creates a New node, stores it in the list and returns a string
        /// for the frequency of that node.
        private String PrintFreq(int[] array)
        {
            String finalString = "";
            for (int i = 0; i < array.Length; i++)
            {
                if (array[i] != 0)
                {
                    Node newNode = new Node();
                    if (i == 0)
                    {
                        finalString += "space:";
                        newNode.SetSymbol(' ');
                    }
                    else if ((i >= 1) && (i <= 26))
                    {
                        finalString += (char)(i + 64) + ":";
                        newNode.SetSymbol((char)(i + 64));
                    }
                    else if ((i >= 27) && (i <= 52))
                    {
                        finalString += (char)(i + 70) + ":";
                        newNode.SetSymbol((char)(i + 70));
                    }
                    else if ((i >= 53) && (i <= 62))
                    {
                        finalString += (char)(i - 5) + ":";
                        newNode.SetSymbol((char)(i - 5));
                    }
                    else
                    {
                        finalString += "new line:";
                        newNode.SetSymbol('\n');
                    }
                    newNode.SetFreq(array[i]);
                    freqList.Add(newNode);
                    finalString += array[i] + "\n";
                }
            }
            return(finalString);
        }

        /// Retrieves the frequency list
        public List<Node> GetFreqList()
        {
            return freqList;
        }

        /// Checks if a character is valid before putting it in the list.
        public bool CharIsValid(char c)
        {
            if ((c == '\n') || (c == '\r') || (c == ' ') || (c >= 'a' && c <= 'z')
               || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
