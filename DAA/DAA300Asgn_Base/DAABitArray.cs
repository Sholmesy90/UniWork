using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    /// <summary>
    /// Class to handle an array of bits
    /// 
    /// </summary>
    public class DAABitArray
    {
        private List<bool> m_bits;


        /// <summary>
        /// Initialises an empty bit array
        /// </summary>
        public DAABitArray()
        {
            m_bits = new List<bool>(); 
        }

        /// <summary>
        /// Copy constructor
        /// </summary>
        public DAABitArray(DAABitArray other)
        {
            m_bits = new List<bool>(other.m_bits);
        }

        /// <summary>
        /// Append a single bit to the end of the bit array.
        /// </summary>
        /// <param name="bitVal">The bit to append.</param>
        public void Append(bool bitVal)
        {
            // Append the new bit
            m_bits.Add(bitVal);
        }

        /// <summary>
        /// Append a single bit to the end of the bit array.
        /// </summary>
        /// <param name="bitVal">The bit to append.</param>
        public void Append(int bitVal)
        {
            // Append the new bit
            if (bitVal == 0)
                m_bits.Add(false);
            else
                m_bits.Add(true);
        }

        /// <summary>
        /// Append another bit array to the end of this bit array.
        /// </summary>
        /// <param name="bitSet">The bit array to append.</param>
        public void Append(DAABitArray bitSet)
        {
            for (int ii = 0; ii < bitSet.NumBits; ii++) {
                m_bits.Add(bitSet.GetBitAsBool(ii));
            }
        }

        /// <summary>
        /// Append a set of bits in a long to the end of the bit array.
        /// </summary>
        /// <param name="bitSet">The set of bits to append.</param>
        /// <param name="numBits">The number of bits from bitSet to append.</param>
        public void Append(long bitSet, int numBits)
        {
            long mask = 0x01;

            // Start appending the most-significant bit first
            mask = mask << (numBits-1);

            // Append the new bits
            for (int ii = 0; ii < numBits; ii++) {
                if ((mask & bitSet) != 0)
                    m_bits.Add(true);
                else
                    m_bits.Add(false);

                // Shift to next bit in the long
                mask = mask >> 1;
            }
        }

        /// <summary>
        /// Clone (deep copy) this bit array.
        /// </summary>
        public DAABitArray Clone()
        {
            return new DAABitArray(this);
        }

        /// <summary>
        /// Delete the last bit from the bit array.
        /// </summary>
        public void RemoveLastBit()
        {
            // Remove the last bit added
            m_bits.RemoveAt(m_bits.Count - 1);
        }


        /// <summary>
        /// Reverse the bit array.
        /// </summary>
        public void Reverse()
        {
            m_bits.Reverse();
        }

        /// <summary>
        /// Number of bits in the bit array.
        /// </summary>
        public int NumBits
        {
            get { return m_bits.Count; }
        }

        /// <summary>
        /// Get a bit as a Boolean value.
        /// </summary>
        /// <param name="bitIndex">Index of the bit to get (zero-based).</param>
        /// <returns>The bit value at bitIndex.</returns>
        public bool GetBitAsBool(int bitIndex)
        {
            return m_bits[bitIndex];
        }

        /// <summary>
        /// Get a bit as a Boolean value, using indexing [] notation.
        /// </summary>
        /// <value>Index of the bit to get (zero-based).</value>
        /// <returns>The bit value at bitIndex.</returns>
        public bool this[int bitIndex]
        {
            get { return m_bits[bitIndex]; }
        }


        /// <summary>
        /// Get a bit as a long int value.
        /// </summary>
        /// <param name="bitIndex">Index of the bit to get (zero-based).</param>
        /// <returns>The bit value at bitIndex.</returns>
        public long GetBitAsLong(int bitIndex)
        {
            bool bThisBit = m_bits[bitIndex];
            if (bThisBit)
                return 0x01;
            else
                return 0x00;
        }

        /// <summary>
        /// Extract a range of bit as a long int value.
        /// </summary>
        /// <param name="startBitIndex">Index of the first bit in the range (zero-based).</param>
        /// <param name="endBitIndex">Index of the last bit in the range (zero-based).</param>
        /// <returns>The long containing the bits requested.</returns>
        public long GetBitRange(int startBitIdx, int endBitIdx)
        {
            int iNumBits;
            long iBitSetVal;

            iNumBits = endBitIdx - startBitIdx + 1;
            if (iNumBits > (sizeof(long) * 8))
                throw new ArgumentOutOfRangeException("Max number of bits is " + sizeof(long) * 8);

            iBitSetVal = 0;
            for (int ii = startBitIdx; ii <= endBitIdx; ii++) {
                // Make room for the next bit
                iBitSetVal = iBitSetVal << 1;

                if (m_bits[ii] == true) {
                    iBitSetVal = iBitSetVal | 0x01;
                }
                // else the bit shift << will have 'added' a 0 on the end anyways
            }

            return iBitSetVal;
        }

    }
}
