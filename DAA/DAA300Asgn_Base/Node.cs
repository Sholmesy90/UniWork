using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    public class Node
    {
        private Node left;
        private Node right;
        private char symbol;
        private int frequency;

        public Node()
        {
            left = null;
            right = null;
            frequency = 0;
            symbol = '!';
        }

        public void SetSymbol(char c)
        {
            symbol = c;
        }

        public void SetLeft(Node n)
        {
            left = n;
        }

        public void SetRight(Node n)
        {
            right = n;
        }

        public void SetFreq(int i)
        {
            frequency = i;
        }

        public char GetSymbol()
        {
            return symbol;
        }

        public Node GetLeft()
        {
            return left;
        }

        public Node GetRight()
        {
            return right;
        }

        public int GetFreq()
        {
            return frequency;
        }

        public bool IsBranch()
        {
            if ((left == null) && (right == null))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }
}
