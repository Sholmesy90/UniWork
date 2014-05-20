using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    public class FreqListGenerator
    {
        private List<Node> freqList;

        public FreqListGenerator()
        {
            freqList = new List<Node>();
        }

        public String CreateFreqTable(char[] charArray)
        {
            int[] frequencyArray = new int[64];
            for (int i = 0; i < charArray.Length; i++)
            {
                int number = 0;
                char c = charArray[i];

                if ((c >= '0') && (c <= '9'))
                    number = (int)c + 5;
                else if ((c >= 'a') && (c <= 'z'))
                    number = (int)c - 70;
                else if ((c >= 'A') && (c <= 'Z'))
                    number = (int)c - 64;
                else if (c == '\n')
                    number = 63;
                else if (c == ' ')
                    number = 0;

                frequencyArray[number]++;
            }
            return (PrintFreq(frequencyArray));
        }

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
                        finalString += "space : ";
                        newNode.SetSymbol(' ');
                    }
                    else if ((i >= 1) && (i <= 26))
                    {
                        finalString += (char)(i + 64) + " : ";
                        newNode.SetSymbol((char)(i + 64));
                    }
                    else if ((i >= 27) && (i <= 52))
                    {
                        finalString += (char)(i + 70) + " : ";
                        newNode.SetSymbol((char)(i + 70));
                    }
                    else if ((i >= 53) && (i <= 62))
                    {
                        finalString += (char)(i - 5) + " : ";
                        newNode.SetSymbol((char)(i - 5));
                    }
                    else
                    {
                        finalString += "new line : ";
                        newNode.SetSymbol('\n');
                    }
                    newNode.SetFreq(array[i]);
                    freqList.Add(newNode);
                    finalString += array[i] + "\n";
                }
            }
            return(finalString);
        }

        public List<Node> GetFreqList()
        {
            return freqList;
        }
    }
}
