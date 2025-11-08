declare module '@apiverve/unitconverter' {
  export interface unitconverterOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface unitconverterResponse {
    status: string;
    error: string | null;
    data: any;
    code?: number;
  }

  export default class unitconverterWrapper {
    constructor(options: unitconverterOptions);

    execute(callback: (error: any, data: unitconverterResponse | null) => void): Promise<unitconverterResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: unitconverterResponse | null) => void): Promise<unitconverterResponse>;
    execute(query?: Record<string, any>): Promise<unitconverterResponse>;
  }
}
