export interface StateType {
  state: string;
  suggested?: boolean;
}

export const states: readonly StateType[] = [
  { state: "NSW" },
  { state: "QLD" },
  { state: "SA" },
  { state: "TAS" },
  { state: "VIC" },
  { state: "WA" },
  { state: "ACT" },
  { state: "NT" },
];

// helper functions to manage type safety
export const convertStateTypeToString = (
  stateType: StateType | null
): string => {
  if (stateType === null) {
    return "";
  }
  return stateType.state;
};
