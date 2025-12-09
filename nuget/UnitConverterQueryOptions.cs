using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace APIVerve.API.UnitConverter
{
    /// <summary>
    /// Query options for the Unit Converter API
    /// </summary>
    public class UnitConverterQueryOptions
    {
        /// <summary>
        /// The value to convert from (e.g., 100)
        /// Example: 100
        /// </summary>
        [JsonProperty("value")]
        public string Value { get; set; }

        /// <summary>
        /// The unit to convert from. Allowed values: lb, kg, oz, g, in, cm, ft, m, mi, km, yd, mm, cm2, m2, mi2, km2, yd2, mm2, c, f, k
        /// Example: lb
        /// </summary>
        [JsonProperty("from")]
        public string From { get; set; }

        /// <summary>
        /// The unit to convert to. Allowed values: lb, kg, oz, g, in, cm, ft, m, mi, km, yd, mm, cm2, m2, mi2, km2, yd2, mm2, c, f, k
        /// Example: kg
        /// </summary>
        [JsonProperty("to")]
        public string To { get; set; }
    }
}
